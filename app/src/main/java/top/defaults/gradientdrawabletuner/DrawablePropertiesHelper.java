package top.defaults.gradientdrawabletuner;

import android.graphics.drawable.GradientDrawable;
import android.util.SparseIntArray;

import top.defaults.drawabletoolbox.DrawableProperties;

public class DrawablePropertiesHelper {

    public static boolean shouldEnableGradient(DrawableProperties properties) {
        return properties != null && properties.useGradient && properties.shape != GradientDrawable.LINE;
    }

    public static boolean shouldEnableCenterColor(DrawableProperties properties) {
        return properties != null && properties.useCenterColor && shouldEnableGradient(properties);
    }

    public static boolean shouldEnableGradientRadius(DrawableProperties properties) {
        return properties != null && properties.type == GradientDrawable.RADIAL_GRADIENT && shouldEnableGradient(properties);
    }

    private static SparseIntArray shapeToIdMap = new SparseIntArray();
    static {
        shapeToIdMap.put(GradientDrawable.RECTANGLE, R.id.rectangle);
        shapeToIdMap.put(GradientDrawable.OVAL, R.id.oval);
        shapeToIdMap.put(GradientDrawable.LINE, R.id.line);
        shapeToIdMap.put(GradientDrawable.RING, R.id.ring);
    }
    public static int getShapeId(DrawableProperties properties) {
        if (properties == null) return R.id.rectangle;
        return shapeToIdMap.get(properties.shape);
    }

    private static SparseIntArray typeToIdMap = new SparseIntArray();
    static {
        typeToIdMap.put(GradientDrawable.LINEAR_GRADIENT, R.id.linear_gradient);
        typeToIdMap.put(GradientDrawable.RADIAL_GRADIENT, R.id.radial_gradient);
        typeToIdMap.put(GradientDrawable.SWEEP_GRADIENT, R.id.sweep_gradient);
    }

    public static int getTypeId(DrawableProperties properties) {
        if (properties == null) return R.id.linear_gradient;
        return typeToIdMap.get(properties.type);
    }
}
