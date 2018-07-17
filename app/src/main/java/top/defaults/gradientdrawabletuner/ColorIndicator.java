package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import top.defaults.view.ColorPickerPopup;

public class ColorIndicator extends AppCompatTextView {

    private int indicatorSize;
    private int currentColor;

    public ColorIndicator(Context context) {
        this(context, null);
    }

    public ColorIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float density = getResources().getDisplayMetrics().density;
        indicatorSize = (int) (20 * density);
        int drawablePadding = (int) (6 * density);
        setCompoundDrawablePadding(drawablePadding);
        int padding = (int) (6 * density);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER);
        setOnClickListener(v -> {
            ColorPickerPopup popup = new ColorPickerPopup.Builder(context)
                    .initialColor(getColor())
                    .build();
            popup.show(v, new ColorPickerPopup.ColorPickerObserver() {
                @Override
                public void onColorPicked(int color) {
                    if (listener != null) {
                        listener.onColorChange(ColorIndicator.this, color);
                    }
                }

                @Override
                public void onColor(int color, boolean fromUser) {

                }
            });
        });
    }

    public void setColor(int color) {
        currentColor = color;
        GradientDrawable left = new GradientDrawable();
        left.setColor(currentColor);
        left.setCornerRadius(indicatorSize / 2);
        left.setStroke(1, Color.LTGRAY);
        left.setBounds(0, 0, indicatorSize, indicatorSize);
        setCompoundDrawables(left, null, null, null);
    }

    public int getColor() {
        return currentColor;
    }

    private OnColorChangeListener listener;

    public void setListener(OnColorChangeListener listener) {
        this.listener = listener;
    }

    public interface OnColorChangeListener {
        void onColorChange(ColorIndicator view, int color);
    }

    @BindingAdapter("onColorChange")
    public static void setColorChangeListener(ColorIndicator view, OnColorChangeListener oldListener, OnColorChangeListener listener) {
        if (oldListener != null) {
            view.setListener(null);
        }
        if (listener != null) {
            view.setListener(listener);
        }
    }
}
