package top.defaults.gradientdrawabletuner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.gradientdrawabletuner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.shape) RadioGroup shapeSwitcher;
    @BindView(R.id.cornerRadiusRow) ValueRow cornerRadiusRow;
    @BindView(R.id.fourCorners) Group fourCorners;

    private GradientDrawableViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.crafting_shape));
        ShapeXmlGenerator.init(this);

        viewModel = ViewModelProviders.of(this).get(GradientDrawableViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        GradientDrawableProperties originProperties = viewModel.getDrawableProperties().getValue();

        ButterKnife.bind(this);

        Resources resources = getResources();
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
                if (properties.shape != GradientDrawable.RING) {
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

        // Set initial width/height/gradient-radius here, because data binding's SeekBar will
        // fire a progress update to 100 when initialing
        imageView.post(() -> viewModel.updateProperties(properties -> {
            properties.width = originProperties.width;
            properties.height = originProperties.height;
            properties.setGradientRadius(originProperties.getGradientRadius());
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                viewModel.reset();
                break;
            case R.id.code:
                Intent intent = new Intent(this, XmlCodeViewActivity.class);
                intent.putExtra(XmlCodeViewActivity.EXTRA_PROPERTIES, viewModel.getDrawableProperties().getValue());
                startActivity(intent);
                break;
            case R.id.save:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
