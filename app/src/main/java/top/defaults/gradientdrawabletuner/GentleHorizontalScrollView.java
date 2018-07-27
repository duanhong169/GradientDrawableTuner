package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class GentleHorizontalScrollView extends HorizontalScrollView {

    private int lastX;
    private int lastY;
    private boolean intercepting = false;

    public GentleHorizontalScrollView(Context context) {
        this(context, null);
    }

    public GentleHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GentleHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepting = true;
                lastX = x;
                lastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                if (intercepting) {
                    intercepting = Math.abs(deltaX) > Math.abs(deltaY);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepting = true;
                break;
            }
        }
        return intercepting && super.onInterceptTouchEvent(ev);
    }
}
