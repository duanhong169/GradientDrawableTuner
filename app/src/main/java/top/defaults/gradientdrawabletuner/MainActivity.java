package top.defaults.gradientdrawabletuner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
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
    @BindView(R.id.strokeColor) ColorIndicator strokeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GradientDrawableViewModel viewModel = ViewModelProviders.of(this).get(GradientDrawableViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        Resources resources = getResources();
        float density = resources.getDisplayMetrics().density;
        final int maxWidthDp = (int) (resources.getDisplayMetrics().widthPixels / density / 1.5);
        binding.setMaxWidthDp(maxWidthDp);
        final int maxHeightDp = (int) (resources.getDisplayMetrics().heightPixels / density / 1.5);
        binding.setMaxHeightDp(maxHeightDp);

        viewModel.getDrawableProperties().observe(this, properties -> {
            if (properties != null) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) (properties.width * density),
                        (int) (properties.height * density));
                params.gravity = Gravity.CENTER_HORIZONTAL;
                int margin = (int) (8 * density);
                params.setMargins(margin, margin, margin, 0);
                imageView.setLayoutParams(params);
            }
        });
    }
}
