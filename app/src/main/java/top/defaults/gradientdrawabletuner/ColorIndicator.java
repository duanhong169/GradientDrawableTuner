package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import top.defaults.view.ColorPickerPopup;

public class ColorIndicator extends View {

    public ColorIndicator(Context context) {
        this(context, null);
    }

    public ColorIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(v -> {
            Drawable background = getBackground();
            int currentColor = Color.WHITE;
            if (background instanceof ColorDrawable) {
                currentColor = ((ColorDrawable) background).getColor();
            }

            ColorPickerPopup popup = new ColorPickerPopup.Builder(context)
                    .initialColor(currentColor)
                    .build();
            popup.show(v, new ColorPickerPopup.ColorPickerObserver() {
                @Override
                public void onColorPicked(int color) {
                    v.setBackgroundColor(color);
                }

                @Override
                public void onColor(int color, boolean fromUser) {

                }
            });
        });
    }
}
