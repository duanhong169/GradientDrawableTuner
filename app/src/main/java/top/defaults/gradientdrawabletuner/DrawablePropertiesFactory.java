package top.defaults.gradientdrawabletuner;

import android.graphics.drawable.GradientDrawable;

import top.defaults.gradientdrawabletuner.db.DrawablePropertiesInRoom;

public class DrawablePropertiesFactory {

    public static DrawablePropertiesInRoom createDefault() {
        return new DrawablePropertiesInRoom();
    }

    public static DrawablePropertiesInRoom createRectangleSample() {
        DrawablePropertiesInRoom properties = new DrawablePropertiesInRoom();
        properties.shape = GradientDrawable.RECTANGLE;
        properties.topLeftRadius = 60;
        properties.topRightRadius = 60;
        properties.useGradient = true;
        properties.type = GradientDrawable.RADIAL_GRADIENT;
        properties.gradientRadius = 520f;
        properties.centerX = 0.5f;
        properties.centerY = 1.0f;
        properties.strokeWidth = 4;
        properties.dashWidth = 20;
        properties.dashGap = 12;
        return properties;
    }
}
