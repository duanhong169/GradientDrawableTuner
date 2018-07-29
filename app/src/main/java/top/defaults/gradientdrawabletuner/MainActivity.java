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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.gradientdrawabletuner.databinding.ActivityMainBinding;
import top.defaults.gradientdrawabletuner.db.AppDatabase;
import top.defaults.gradientdrawabletuner.db.DrawableSpec;
import top.defaults.gradientdrawabletuner.db.DrawableSpecFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.statusTextView) TextView statusTextView;
    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.shape) RadioGroup shapeSwitcher;
    @BindView(R.id.cornerRadiusRow) ValueRow cornerRadiusRow;
    @BindView(R.id.fourCorners) Group fourCorners;

    private GradientDrawableViewModel viewModel;
    private DrawableSpec currentDrawableSpec = DrawableSpecFactory.tempSpec();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.crafting_shape));
        ShapeXmlGenerator.init(this);

        viewModel = ViewModelProviders.of(this).get(GradientDrawableViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        viewModel.apply(currentDrawableSpec.getProperties());

        ButterKnife.bind(this);

        updateStatus();
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
                AppDatabase.getInstance(this).drawableSpecDao().getAll().observe(this, drawableSpecs -> {
                    if (drawableSpecs != null && drawableSpecs.size() > 0) {
                        new DrawableSpecChooser(this, drawableSpecs).show(imageView, drawableSpec -> {
                            currentDrawableSpec = drawableSpec;
                            viewModel.apply(currentDrawableSpec.getProperties());
                            updateStatus();
                        });
                    }
                });
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

    private void updateStatus() {
        statusTextView.setText(String.format("Spec: [%s][Edited]", currentDrawableSpec.getName()));
    }
}
