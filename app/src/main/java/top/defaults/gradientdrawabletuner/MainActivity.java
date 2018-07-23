package top.defaults.gradientdrawabletuner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.gradientdrawabletuner.databinding.ActivityMainBinding;
import top.defaults.checkerboarddrawable.CheckerboardDrawable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.background) View background;
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
        CheckerboardDrawable drawable = new CheckerboardDrawable.Builder()
                .size(30).build();
        background.setBackgroundDrawable(drawable);
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
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
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
