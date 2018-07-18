package top.defaults.gradientdrawabletuner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
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
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        properties.width + properties.strokeWidth,
                        properties.height + properties.strokeWidth);
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
