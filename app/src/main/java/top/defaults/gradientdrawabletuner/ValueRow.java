package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class ValueRow extends LinearLayout {

    private TextView titleTextView;
    private SeekBar valueSeekBar;
    private String title;

    public ValueRow(Context context) {
        this(context, null);
    }

    public ValueRow(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValueRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ValueRow);
        title = typedArray.getString(R.styleable.ValueRow_title);
        typedArray.recycle();

        float density = context.getResources().getDisplayMetrics().density;
        int padding = (int) (4 * density);
        setPadding(padding, padding, padding, padding);
        {
            titleTextView = new TextView(context);
            LinearLayout.LayoutParams params = new LayoutParams((int) (88 * density),
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(titleTextView, params);
        }

        {
            valueSeekBar = new SeekBar(context);
            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(valueSeekBar, params);
            valueSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b && listener != null) {
                        listener.onValueChange(ValueRow.this, i);
                    }
                }
            });
        }
    }

    public void setMax(int max) {
        valueSeekBar.setMax(max);
    }

    public void setValueForTitle(Object valueForTitle) {
        titleTextView.setText(String.format(Locale.getDefault(), "%s: %s", title, valueForTitle));
    }

    @InverseBindingAdapter(attribute = "value")
    public static int getValue(ValueRow valueRow) {
        return valueRow.valueSeekBar.getProgress();
    }

    @BindingAdapter("value")
    public static void setValue(ValueRow valueRow, int value) {
        valueRow.setValue(value);
    }

    @BindingAdapter(value = "valueAttrChanged")
    public static void setListener(ValueRow valueRow, final InverseBindingListener listener) {
        if (listener != null) {
            valueRow.valueSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (b) {
                        listener.onChange();
                    }
                }
            });
        }
    }

    public void setValue(int value) {
        valueSeekBar.setProgress(value);
    }

    private OnValueChangeListener listener;

    public void setListener(OnValueChangeListener listener) {
        this.listener = listener;
    }

    public interface OnValueChangeListener {
        void onValueChange(ValueRow view, int value);
    }

    @BindingAdapter("onValueChange")
    public static void setListener(ValueRow view, OnValueChangeListener listener) {
        view.setListener(listener);
    }
}
