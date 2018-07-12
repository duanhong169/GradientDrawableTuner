package top.defaults.gradientdrawabletuner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import top.defaults.gradientdrawabletuner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final GradientDrawableViewModel viewModel = ViewModelProviders.of(this).get(GradientDrawableViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        Resources resources = getResources();
        final int maxRadiusDp = (int) (resources.getDimension(R.dimen.drawableWidth) / resources.getDisplayMetrics().density / 2);
        binding.setMaxRadiusDp(maxRadiusDp);
    }
}
