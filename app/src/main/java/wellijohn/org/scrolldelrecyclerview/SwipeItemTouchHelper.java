package wellijohn.org.scrolldelrecyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.Map;
import java.util.TreeMap;

import wellijohn.org.scrolldelrecyclerview.delrecyclerview.constant.Constant;

/**
 * @author: JiangWeiwei
 * @time: 2018/4/8-20:53
 * @email: wellijohn1991@gmail.com
 * @desc:
 */
public class SwipeItemTouchHelper extends ItemTouchHelper {
    static int i = 1;

    public SwipeItemTouchHelper(Callback callback) {
        super(callback);
    }


    public static class SwipeCallBack extends Callback {

        private Map<Integer, Boolean> swipeStateMap = new TreeMap<>();

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;        //允许上下的拖动
            return makeMovementFlags(dragFlags, ItemTouchHelper.LEFT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            Log.d(Constant.KEY_LOG, "onSwiped: " + direction);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            int adapterPosition = viewHolder.getAdapterPosition();
            View rightMenu = viewHolder.itemView.findViewById(R.id.right_menu_content);
            View content = viewHolder.itemView.findViewById(R.id.ll_content);


            final float dir = Math.signum(dX);
            Log.d(Constant.KEY_LOG, "onChildDraw: " + dir);
            float translationX = Math.min(-dX, rightMenu.getMeasuredWidth());
//            viewHolder.itemView.setTranslationX(-translationX);
//            if (dX > ARCHIVE_LAYOUT_WIDTH) {
                getDefaultUIUtil().onDraw(c, recyclerView, content, dX, dY, actionState, isCurrentlyActive);
//            }
//            if (dir == 0) {
//                touchVH.overlay.setTranslationX(-touchVH.overlay.getWidth());
//            } else {
//                final float overlayOffset = dX - dir * viewHolder.itemView.getWidth();
//                touchVH.overlay.setTranslationX(overlayOffset);
//            }
//            float alpha = (float) (.2 + .8 * Math.abs(dX) / viewHolder.itemView.getWidth());
//            touchVH.it
//                    .setAlpha(alpha);


//
//            if (dY != 0 && dX == 0) super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
////            RVSwipeAdapter.ItemBaseViewHolder holder = (RVSwipeAdapter.ItemBaseViewHolder) viewHolder;
////            if (viewHolder instanceof RVSwipeAdapter.ItemSwipeWithActionWidthNoSpringViewHolder) {
//                if (dX < -rightMenu.getWidth()) {
//                    dX = -rightMenu.getWidth();
//                }
//            viewHolder.itemView.setTranslationX(dX);
//            super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);


        }



    }

}

