package wellijohn.org.scrolldelrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author: JiangWeiwei
 * @time: 2018/4/13-19:34
 * @email: wellijohn1991@gmail.com
 * @desc:
 */
public class InterceptScrollView extends ScrollView {
    public InterceptScrollView(Context context) {
        super(context);
    }

    public InterceptScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
