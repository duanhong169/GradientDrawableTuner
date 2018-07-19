package top.defaults.gradientdrawabletuner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.gradientdrawabletuner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView) ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GradientDrawableViewModel viewModel = ViewModelProviders.of(this).get(GradientDrawableViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        binding.setLifecycleOwner(this);

        Resources resources = getResources();
        float density = resources.getDisplayMetrics().density;
        final int maxWidth = (int) (resources.getDisplayMetrics().widthPixels / 2.5);
        binding.setMaxWidth(maxWidth);
        final int maxHeight = (int) (resources.getDisplayMetrics().heightPixels / 2.5);
        binding.setMaxHeight(maxHeight);

        binding.setViewModel(viewModel);

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

        // Set initial width/height here, because data binding's SeekBar will
        // fire a progress update to 100 when initialing
        imageView.post(() -> viewModel.updateProperties(properties -> {
            properties.width = 400;
            properties.height = 400;
        }));
    }
}
