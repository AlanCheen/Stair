package yfy.github.stair.data;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by 程序亦非猿 on 16/1/20.
 */
public class VerItemDecoration extends RecyclerView.ItemDecoration {


    private static final String TAG = "DividerItemDecoration";
    private Paint mPaint;


    public VerItemDecoration() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
    }

    @Override

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        Log.d(TAG, "onDraw() called with: " + "c = [" + c + "], parent = [" + parent + "], state = [" + state + "]");
//        c.drawColor(Color.BLUE);

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + 5;
            c.drawLine(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.d(TAG, "onDrawOver() called with: " + "c = [" + c + "], parent = [" + parent + "], state = [" + state + "]");
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        Log.d(TAG, "getItemOffsets() called with: " + "outRect = [" + outRect + "], view = [" + view + "], parent = [" + parent + "], state = [" + state + "]");
        outRect.set(0, 0, 0, 1);
    }
}
