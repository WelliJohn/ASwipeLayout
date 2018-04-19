package wellijohn.org.scrolldelrecyclerview.delrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import wellijohn.org.scrolldelrecyclerview.R;

/**
 * @author: JiangWeiwei
 * @time: 2018/3/29-20:23
 * @email: wellijohn1991@gmail.com
 * @desc:
 */
public class DelRecyclerView extends RecyclerView {


    public DelRecyclerView(Context context) {
        super(context);
    }

    public DelRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

//        for (int i = 0, size = getChildCount(); i < size; i++) {
//            View childView = getChildAt(i);
//            CoustomViewHolder vh;
//            if (childView.getTag() != null) {
//                vh = (CoustomViewHolder) childView.getTag();
//            } else {
//                vh = new CoustomViewHolder(childView);
//                childView.setTag(vh);
//            }
//            int measuredChildHeight = childView.getMeasuredHeight();
//
//            childView.layout(l, t, r + 980, b );
//
//            vh.llContent.layout(l, t + measuredChildHeight * i, r, b + measuredChildHeight * (i + 1));
//            vh.rightMenuContent.layout(l + 980,
//                    t + measuredChildHeight * i, r + 980, b + measuredChildHeight * (i + 1));
//
//        }
    }

    private void checkAdapter() {
        if (getChildCount() > 0 && getChildAt(0).getId() != R.id.scroll_del_ll) {
            throw new IllegalStateException("adapter must extends RVSwipeAdapter");
        }
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
//        checkAdapter();
//        int widthParentMeasureMode = MeasureSpec.getMode(widthSpec);
//        int widthParentMeasureSize = MeasureSpec.getSize(widthSpec);
//        int heightParentMeasureMode = MeasureSpec.getMode(heightSpec);
//        int heightParentMeasureSize = MeasureSpec.getSize(heightSpec);
//
//        int childViewWidth = 0;
//        int childViewHeight = 0;
//        int childViewWidthMode;
//        int childViewHeightMode;
//
//        for (int i = 0, size = getChildCount(); i < size; i++) {
//            View childView = getChildAt(i);
//                // Measure the child.
////                measureChildWithMargins(childView, widthSpec, 0, heightSpec, 0);
//                CoustomViewHolder vh;
//                if (childView.getTag() != null) {
//                    vh = (CoustomViewHolder) childView.getTag();
//                } else {
//                    vh = new CoustomViewHolder(childView);
//                    childView.setTag(vh);
//                }
//
//                //构造ChildView的widthSpec, int heightSpec
////                getChildViewMeasureSpec(childView, widthSpec, heightSpec);
//
//                //一层一层进行绘制
//                childView.measure(MeasureSpec.makeMeasureSpec(widthParentMeasureSize + 500
//                        , MeasureSpec.EXACTLY), heightSpec);
//
//                vh.llContent.measure(MeasureSpec.makeMeasureSpec(widthParentMeasureSize - 500, MeasureSpec.EXACTLY),
//                        heightSpec);
//                vh.rightMenuContent.measure(MeasureSpec.makeMeasureSpec(500, widthParentMeasureMode), heightSpec);
//
//            }
//            setMeasuredDimension(widthParentMeasureSize + 500,heightParentMeasureSize);

    }

    private MeasureSpec getChildViewMeasureSpec(View childView, int parentWidthSpec, int parentHeightSpec, int rightMenuWidth) {
        int widthParentMeasureMode = MeasureSpec.getMode(parentWidthSpec);
        int widthParentMeasureSize = MeasureSpec.getSize(parentWidthSpec);
        int heightParentMeasureMode = MeasureSpec.getMode(parentHeightSpec);
        int heightParentMeasureSize = MeasureSpec.getSize(parentHeightSpec);
        ViewGroup.LayoutParams childLP = childView.getLayoutParams();

        int widthChildMeasureMode;
        int widthChildMeasureSize;

        switch (widthParentMeasureMode) {
            case MeasureSpec.AT_MOST:
                if (childLP.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    widthChildMeasureSize = widthParentMeasureSize + rightMenuWidth;
                }

                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.EXACTLY:

                break;

        }


        return null;
    }

    public static class CoustomViewHolder {
        LinearLayout scrollDelLl;
        LinearLayout llContent;
        LinearLayout rightMenuContent;

        CoustomViewHolder(View view) {
            scrollDelLl = view.findViewById(R.id.scroll_del_ll);
            llContent = view.findViewById(R.id.ll_content);
            rightMenuContent = view.findViewById(R.id.right_menu_content);
        }
    }
}
