package top.defaults.gradientdrawabletuner;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XmlCodeViewActivity extends AppCompatActivity {

    static final String EXTRA_PROPERTIES = "extra_code";

    @BindView(R.id.xmlCodeTextView) TextView xmlCodeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.review_code);
        setContentView(R.layout.activity_xml_code_view);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        GradientDrawableProperties properties = getIntent().getParcelableExtra(EXTRA_PROPERTIES);
        xmlCodeTextView.setText(ShapeXmlGenerator.shapeXmlString(properties));
        Typeface typeface = Fonts.getDefault(this);
        if (typeface != null) {
            xmlCodeTextView.setTypeface(typeface);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
