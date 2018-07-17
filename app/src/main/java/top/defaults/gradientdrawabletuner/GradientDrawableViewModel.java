package top.defaults.gradientdrawabletuner;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class GradientDrawableViewModel extends AndroidViewModel {

    private Resources resources;
    private MediatorLiveData<GradientDrawable> gradientDrawable = new MediatorLiveData<>();
    private MutableLiveData<GradientDrawableProperties> drawableProperties = new MutableLiveData<>();

    public GradientDrawableViewModel(@NonNull Application application) {
        super(application);
        resources = application.getResources();
        gradientDrawable.addSource(drawableProperties, properties -> {
            GradientDrawable drawable = gradientDrawable.getValue();
            if (drawable == null) drawable = new GradientDrawable();
            if (properties == null) properties = new GradientDrawableProperties();

            drawable.setShape(properties.shape);
            drawable.setCornerRadius(dpToPx(properties.cornerRadius));
            drawable.setSize((int)dpToPx(properties.width), (int)dpToPx(properties.height));
            drawable.setColor(properties.solidColor);
            drawable.setStroke(properties.strokeWidth, properties.strokeColor);
            gradientDrawable.setValue(drawable);
        });
        drawableProperties.setValue(new GradientDrawableProperties());
    }

    public MutableLiveData<GradientDrawable> getGradientDrawable() {
        return gradientDrawable;
    }

    public MutableLiveData<GradientDrawableProperties> getDrawableProperties() {
        return drawableProperties;
    }

    public void shapeIdChanged(int shapeId) {
        updateProperties(properties -> properties.setShapeById(shapeId));
    }

    public void cornerRadiusChanged(int radius) {
        updateProperties(properties -> properties.cornerRadius = radius);
    }

    public void widthChanged(int width) {
        updateProperties(properties -> properties.width = width);
    }

    public void heightChanged(int height) {
        updateProperties(properties -> properties.height = height);
    }

    public void strokeColorChanged(int color) {
        updateProperties(properties -> properties.strokeColor = color);
    }

    public void solidColorChanged(int color) {
        updateProperties(properties -> properties.solidColor = color);
    }

    private interface Callback<T> {
        void onData(T data);
    }

    private void updateProperties(Callback<GradientDrawableProperties> callback) {
        GradientDrawableProperties properties = drawableProperties.getValue();
        if (properties == null) properties = new GradientDrawableProperties();
        callback.onData(properties);
        drawableProperties.setValue(properties);
    }

    public float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
