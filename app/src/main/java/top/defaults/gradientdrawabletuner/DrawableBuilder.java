package top.defaults.gradientdrawabletuner;

import android.graphics.drawable.GradientDrawable;

public class DrawableBuilder {

    private GradientDrawableProperties properties;

    public DrawableBuilder properties(GradientDrawableProperties properties) {
        this.properties = properties;
        return this;
    }

    public GradientDrawable build() {
        GradientDrawable drawable = new GradientDrawable();
        if (properties == null) properties = GradientDrawableProperties.Factory.createDefault();

        drawable.setShape(properties.shape);
        if (properties.shape == GradientDrawable.RING) {
            Reflections.setInnerRadius(drawable, properties.innerRadius);
            Reflections.setInnerRadiusRatio(drawable, properties.innerRadiusRatio);
            Reflections.setThickness(drawable, properties.thickness);
            Reflections.setThicknessRatio(drawable, properties.thicknessRatio);
            Reflections.setUseLevelForShape(drawable, false);
        }
        drawable.setCornerRadii(properties.getCornerRadii());
        if (properties.useGradient) {
            drawable.setGradientType(properties.type);
            drawable.setGradientRadius(properties.getGradientRadius());
            drawable.setGradientCenter(properties.centerX, properties.centerY);
            drawable.setOrientation(properties.getOrientation());
            drawable.setColors(properties.getColors());
        } else {
            drawable.setColor(properties.solidColor);
        }
        drawable.setSize(properties.width + properties.strokeWidth, properties.height + properties.strokeWidth);
        drawable.setStroke(properties.strokeWidth, properties.strokeColor, properties.dashWidth, properties.dashGap);
        return drawable;
    }

}
