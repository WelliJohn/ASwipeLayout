package wellijohn.org.swipevg;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import wellijohn.org.swipevg.inter.OnSwipeStateChangeListener;

/**
 * @author: JiangWeiwei
 * @time: 2018/4/3-15:46
 * @email: wellijohn1991@gmail.com
 * @desc: 用来做侧滑使用
 */
public class SwipeLayout extends LinearLayout {

    private static final int DURATION = 200;
    private OverScroller mScroller;

    //内容的ViewGroup
    private LinearLayout mLlContent;
    //右侧menu
    private LinearLayout mRightMenuContent;
    //速度计算器
    private VelocityTracker mVelocityTracker;

    //能计算出的fling速度
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private float curX;
    private boolean mIsBeingDragged;

    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int mActivePointerId = ViewDragHelper.INVALID_POINTER;
    /**
     * 上一次motionEvent的x位置
     */
    private int mLastMotionX;


    public SwipeLayout(Context context) {
        super(context);
    }

    public SwipeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new OverScroller(getContext(), new FastOutLinearInInterpolator());
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthParentMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        if (mLlContent == null || mRightMenuContent == null) {
            mLlContent = findViewById(R.id.ll_content);
            mRightMenuContent = findViewById(R.id.right_menu_content);
        }

        measureChildWithMargins(mLlContent,
                MeasureSpec.makeMeasureSpec(widthParentMeasureSize, MeasureSpec.EXACTLY), 0, heightMeasureSpec, 0);
        measureChildWithMargins(mRightMenuContent,
                widthMeasureSpec, 0, MeasureSpec.makeMeasureSpec(mLlContent.getMeasuredHeight(), MeasureSpec.EXACTLY), 0);

        setMeasuredDimension(mLlContent.getMeasuredWidth(),
                mLlContent.getMeasuredHeight());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                /*
                 * If being flinged and user touches, stop the fling. isFinished
                 * will be false if being flinged.
                 */
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                resetTouchState();
                if (BuildConfig.DEBUG)
                    Log.d(Constant.KEY_LOG, "curX: " + curX);
                /**
                 * 多点触摸计算
                 */
                mActivePointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int scrollX = getScrollX();
                int measuredWidth = mRightMenuContent.getMeasuredWidth();
                if (BuildConfig.DEBUG)
                    Log.d(Constant.KEY_LOG, "move: " + scrollX);

                float moveX = event.getX();
                int distanceX = (int) (curX - moveX);
                if (distanceX + scrollX < 0) {
                    distanceX = 0 - scrollX;
                }
                if (distanceX + scrollX > measuredWidth) {
                    distanceX = measuredWidth - scrollX;
                }
                if (curX != 0)
                    scrollBy(distanceX, 0);
                curX = moveX;

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetTouchState();
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) velocityTracker.getXVelocity(mActivePointerId);

                if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                    fling(-initialVelocity);
                }
                swipeOrOpenMenu(-initialVelocity);
                recycleVelocityTracker();
                break;
        }


        return true;
    }

    private void swipeOrOpenMenu(int velocity) {
        if (BuildConfig.DEBUG)
            Log.d(Constant.KEY_LOG, "swipeOrOpenMenu: ----->" + velocity);
        int scrollX = getScrollX();
        int measuredWidth = mRightMenuContent.getMeasuredWidth();
        int halfScrollWidth = measuredWidth / 2;
        if (Math.abs(velocity) <= mMinimumVelocity) {
            if (scrollX >= halfScrollWidth) {
                smoothOpenMenu(DURATION);
            } else {
                smoothCloseMenu(DURATION);
            }
        } else {
            if (velocity < 0) {
                smoothCloseMenu(DURATION);
            } else {
                smoothOpenMenu(DURATION);
            }
        }

    }

    private OnSwipeStateChangeListener onSwipeStateChangeListener;

    private void smoothOpenMenu(int duration) {
        if (onSwipeStateChangeListener != null) onSwipeStateChangeListener.onSwipeStateChange(true);
        if (!mScroller.isFinished())
            mScroller.forceFinished(true);
        int scrollX = getScrollX();
        mScroller.startScroll(Math.abs(scrollX), 0,
                mRightMenuContent.getMeasuredWidth() - Math.abs(scrollX), 0, duration);
        invalidate();
    }

    private void smoothCloseMenu(int duration) {
        if (onSwipeStateChangeListener != null)
            onSwipeStateChangeListener.onSwipeStateChange(false);
        if (!mScroller.isFinished())
            mScroller.forceFinished(true);
        int scrollX = getScrollX();
        mScroller.startScroll(Math.abs(scrollX), 0,
                -Math.abs(scrollX), 0, duration);
        invalidate();
    }

    private void resetTouchState() {
        curX = 0;
    }

    private void fling(int xInitialVelocity) {
        if (BuildConfig.DEBUG)
            Log.d(Constant.KEY_LOG, "fling: " + xInitialVelocity);
        if (getChildCount() > 0) {
            mScroller.fling(getScrollX(), getScrollY(), xInitialVelocity, 0, 0,
                    mRightMenuContent.getMeasuredWidth(),
                    0, 0);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onMotionEvent will be called and we do the actual
         * scrolling there.
         */

        /*
         * Shortcut the most recurring case: the user is in the dragging
         * state and he is moving his finger.  We want to intercept this
         * motion.
         */
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
            return true;
        }

        if (super.onInterceptTouchEvent(ev)) {
            return true;
        }


        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
                final int activePointerId = mActivePointerId;
                if (activePointerId == ViewDragHelper.INVALID_POINTER) {
                    break;
                }

                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) {
                    if (BuildConfig.DEBUG)
                        Log.e(Constant.KEY_LOG, "Invalid pointerId=" + activePointerId
                                + " in onInterceptTouchEvent");
                    break;
                }

                final int x = (int) ev.getX(pointerIndex);
                final int xDiff = Math.abs(x - mLastMotionX);
                if (xDiff > mTouchSlop) {
                    mIsBeingDragged = true;
                    mLastMotionX = x;
                    initVelocityTrackerIfNotExists();
                    mVelocityTracker.addMovement(ev);
                    final ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                mLastMotionX = (int) ev.getX();
                mActivePointerId = ev.getPointerId(0);

                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                /*
                 * If being flinged and user touches the screen, initiate drag;
                 * otherwise don't. mScroller.isFinished should be false when
                 * being flinged. We need to call computeScrollOffset() first so that
                 * isFinished() is correct.
                 */
                mScroller.computeScrollOffset();
                mIsBeingDragged = !mScroller.isFinished();
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                /* Release the drag */
                mIsBeingDragged = false;
                mActivePointerId = ViewDragHelper.INVALID_POINTER;
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }

        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         */
        return mIsBeingDragged;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = (int) ev.getX(newPointerIndex);
            mActivePointerId = ev.getPointerId(newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }


    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            if (oldX != x || oldY != y) {
                scrollBy(x - oldX, y - oldY);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    public void setOpen(final boolean open) {
        if (open) {
            scrollTo(mRightMenuContent.getMeasuredWidth(), 0);
        } else {
            scrollTo(0, 0);
        }
    }


    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void setOnSwipeStateChangeListener(OnSwipeStateChangeListener onSwipeStateChangeListener) {
        this.onSwipeStateChangeListener = onSwipeStateChangeListener;
    }
}
