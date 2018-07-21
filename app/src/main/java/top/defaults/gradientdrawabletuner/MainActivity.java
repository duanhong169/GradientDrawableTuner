package top.defaults.gradientdrawabletuner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.gradientdrawabletuner.databinding.ActivityMainBinding;
import top.defaults.view.CheckerboardDrawable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.shape) RadioGroup shapeSwitcher;
    @BindView(R.id.cornerRadiusRow) ValueRow cornerRadiusRow;
    @BindView(R.id.fourCorners) Group fourCorners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GradientDrawableViewModel viewModel = ViewModelProviders.of(this).get(GradientDrawableViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        ButterKnife.bind(this);

        Resources resources = getResources();
        float density = resources.getDisplayMetrics().density;

        CheckerboardDrawable drawable = new CheckerboardDrawable.Builder()
                .size(30).build();
        imageView.setBackgroundDrawable(drawable);
        shapeSwitcher.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != R.id.rectangle) {
                cornerRadiusRow.setExtensionsChecked(false);
            }
            viewModel.updateProperty("shapeId", checkedId);
        });
        cornerRadiusRow.setOnExtensionsCheckedListener(checked -> fourCorners.setVisibility(checked ? View.VISIBLE : View.GONE));

        viewModel.getDrawableProperties().observe(this, properties -> {
            if (properties != null) {
                int width = properties.width;
                int height = properties.height;
                // enlarge the width/height with the strokeWidth for RECTANGLE/OVAL
                if (properties.shape == GradientDrawable.RECTANGLE || properties.shape == GradientDrawable.OVAL) {
                    width = width + properties.strokeWidth;
                    height = height + properties.strokeWidth;
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                int margin = (int) (8 * density);
                params.setMargins(margin, margin, margin, 0);
                imageView.setLayoutParams(params);
            }
        });

        final int maxWidth = (int) (resources.getDisplayMetrics().widthPixels / 1.5);
        final int maxHeight = (int) (resources.getDisplayMetrics().heightPixels / 2.5);
        binding.setMaxWidth(maxWidth);
        binding.setMaxHeight(maxHeight);
        binding.setViewModel(viewModel);

        // Set initial width/height here, because data binding's SeekBar will
        // fire a progress update to 100 when initialing
        imageView.post(() -> viewModel.updateProperties(properties -> {
            properties.width = 400;
            properties.height = 400;
        }));
    }
}
