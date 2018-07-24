package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import top.defaults.checkerboarddrawable.CheckerboardDrawable;
import top.defaults.colorpicker.ColorPickerPopup;

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
                    .enableAlpha(true)
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

        GradientDrawable colorDrawable = new GradientDrawable();
        colorDrawable.setColor(currentColor);
        colorDrawable.setCornerRadius(indicatorSize / 2);
        colorDrawable.setStroke(1, Color.LTGRAY);

        LayerDrawable left = new LayerDrawable(new Drawable[]{backgroundDrawable(), colorDrawable});
        left.setBounds(0, 0, indicatorSize, indicatorSize);
        setCompoundDrawables(left, null, null, null);
    }

    private static Drawable backgroundDrawable;

    public Drawable backgroundDrawable() {
        if (backgroundDrawable == null) {
            Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Bitmap roundedBackgroundBitmap = Bitmap.createBitmap(indicatorSize, indicatorSize, Bitmap.Config.ARGB_8888);
            Canvas roundedBackgroundCanvas = new Canvas(roundedBackgroundBitmap);

            Rect rect = new Rect(0, 0, indicatorSize, indicatorSize);
            RectF rectF = new RectF(rect);

            int radius = indicatorSize / 2;

            backgroundPaint.setXfermode(null);
            roundedBackgroundCanvas.drawRoundRect(rectF, radius, radius, backgroundPaint);

            backgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            CheckerboardDrawable background = new CheckerboardDrawable.Builder().size(radius).build();
            roundedBackgroundCanvas.drawBitmap(fromDrawable(background, indicatorSize), rect, rect, backgroundPaint);

            backgroundDrawable = new BitmapDrawable(getResources(), roundedBackgroundBitmap);
        }
        return backgroundDrawable;
    }

    public Bitmap fromDrawable(Drawable drawable, int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
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
