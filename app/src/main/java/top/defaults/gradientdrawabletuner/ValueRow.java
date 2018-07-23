package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ValueRow extends LinearLayout {

    CheckBox extensionsCheckBox;
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
        setGravity(Gravity.CENTER_VERTICAL);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ValueRow);
        String extensionsName = typedArray.getString(R.styleable.ValueRow_extensionsName);
        title = typedArray.getString(R.styleable.ValueRow_title);
        typedArray.recycle();

        float density = context.getResources().getDisplayMetrics().density;
        int padding = (int) (4 * density);
        setPadding(padding, padding, padding, padding);

        if (!TextUtils.isEmpty(extensionsName)) {
            extensionsCheckBox = new CheckBox(context);
            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = (int) (8 * density);
            extensionsCheckBox.setText(extensionsName);
            addView(extensionsCheckBox, params);
            extensionsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (onExtensionsCheckedListener != null) {
                    onExtensionsCheckedListener.onChecked(isChecked);
                }
            });
        }

        {
            titleTextView = new TextView(context);
            LinearLayout.LayoutParams params = new LayoutParams((int) (96 * density),
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
                    if (b) {
                        notifyValueChange(i);
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

    public void setValue(int value) {
        valueSeekBar.setProgress(value);
    }

    public void setEnabled(boolean enabled) {
        if (extensionsCheckBox != null) {
            extensionsCheckBox.setEnabled(enabled);
        }

        titleTextView.setEnabled(enabled);
        valueSeekBar.setEnabled(enabled);
    }

    private List<OnValueChangeListener> onValueChangeListeners = new ArrayList<>();

    public void addOnValueChangeListener(OnValueChangeListener listener) {
        onValueChangeListeners.add(listener);
    }

    private void notifyValueChange(int value) {
        for (OnValueChangeListener listener : onValueChangeListeners) {
            listener.onValueChange(this, value);
        }
    }

    public interface OnValueChangeListener {
        void onValueChange(ValueRow view, int value);
    }

    @BindingAdapter("onValueChange")
    public static void setListener(ValueRow view, OnValueChangeListener listener) {
        view.addOnValueChangeListener(listener);
    }

    private OnExtensionsCheckedListener onExtensionsCheckedListener;

    public interface OnExtensionsCheckedListener {
        void onChecked(boolean checked);
    }

    public void setOnExtensionsCheckedListener(OnExtensionsCheckedListener listener) {
        onExtensionsCheckedListener = listener;
    }

    public void setExtensionsChecked(boolean isChecked) {
        extensionsCheckBox.setChecked(isChecked);
    }

}
