package top.defaults.gradientdrawabletuner;

import android.graphics.drawable.GradientDrawable;
import android.util.SparseIntArray;

import top.defaults.annotations.AutoProperty;

public class GradientDrawableProperties {

    private static SparseIntArray shapeToIdMap = new SparseIntArray();
    static {
        shapeToIdMap.put(GradientDrawable.RECTANGLE, R.id.rectangle);
        shapeToIdMap.put(GradientDrawable.OVAL, R.id.oval);
        shapeToIdMap.put(GradientDrawable.LINE, R.id.line);
        shapeToIdMap.put(GradientDrawable.RING, R.id.ring);
    }
    private static SparseIntArray idToShapeMap = new SparseIntArray();
    static {
        idToShapeMap.put(R.id.rectangle, GradientDrawable.RECTANGLE);
        idToShapeMap.put(R.id.oval, GradientDrawable.OVAL);
        idToShapeMap.put(R.id.line, GradientDrawable.LINE);
        idToShapeMap.put(R.id.ring, GradientDrawable.RING);
    }

    @AutoProperty("shapeId") public int shape = GradientDrawable.RECTANGLE;
    public int getShapeId() {
        return shapeToIdMap.get(shape);
    }
    public void setShapeId(Integer id) {
        shape = idToShapeMap.get(id);
    }

    // all dimens are in pixels
    @AutoProperty public int innerRadius = -1; // when innerRadius == -1, innerRadiusRatio become effective
    @AutoProperty public float innerRadiusRatio = 9f;
    @AutoProperty public int thickness = -1; // when thickness == -1, thicknessRatio become effective
    @AutoProperty public float thicknessRatio = 3f;

    @AutoProperty("cornerRadius") private int cornerRadius = 0; // This is overridden for each corner by the following 4 properties

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(Integer cornerRadius) {
        if (cornerRadius == null) cornerRadius = 0;
        this.cornerRadius = cornerRadius;
        this.topLeftRadius = cornerRadius;
        this.topRightRadius = cornerRadius;
        this.bottomLeftRadius = cornerRadius;
        this.bottomRightRadius = cornerRadius;
    }

    public float[] getCornerRadii() {
        return new float[] {
                topLeftRadius, topLeftRadius,
                topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius,
                bottomLeftRadius, bottomLeftRadius
        };
    }

    @AutoProperty public int topLeftRadius = 0;
    @AutoProperty public int topRightRadius = 0;
    @AutoProperty public int bottomLeftRadius = 0;
    @AutoProperty public int bottomRightRadius = 0;

    private static SparseIntArray typeToIdMap = new SparseIntArray();
    static {
        typeToIdMap.put(GradientDrawable.LINEAR_GRADIENT, R.id.linear_gradient);
        typeToIdMap.put(GradientDrawable.RADIAL_GRADIENT, R.id.radial_gradient);
        typeToIdMap.put(GradientDrawable.SWEEP_GRADIENT, R.id.sweep_gradient);
    }
    private static SparseIntArray idToTypeMap = new SparseIntArray();
    static {
        idToTypeMap.put(R.id.linear_gradient, GradientDrawable.LINEAR_GRADIENT);
        idToTypeMap.put(R.id.radial_gradient, GradientDrawable.RADIAL_GRADIENT);
        idToTypeMap.put(R.id.sweep_gradient, GradientDrawable.SWEEP_GRADIENT);
    }
    @AutoProperty("typeId") public int type = GradientDrawable.RADIAL_GRADIENT;
    public int getTypeId() {
        return typeToIdMap.get(type);
    }
    public void setTypeId(Integer id) {
        type = idToTypeMap.get(id);
    }

    @AutoProperty public boolean useGradient = false;
    @AutoProperty public boolean useCenterColor = true;

    @AutoProperty public int angle = 0;
    public GradientDrawable.Orientation getOrientation() {
        int angle = this.angle % 360;
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
        switch (angle) {
            case 0:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 45:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 90:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 135:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 180:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 225:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case 270:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 315:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
        }
        return orientation;
    }

    @AutoProperty public float centerX = 0.5f;
    @AutoProperty public float centerY = 0.5f;
    @AutoProperty public int startColor = 0xFF2DCFCA;
    @AutoProperty public int centerColor = 0xFFFFFFFF;
    @AutoProperty public int endColor = 0x7FFFFFFF;

    public int[] getColors() {
        if (useCenterColor) {
            return new int[]{startColor, centerColor, endColor};
        }
        return new int[]{startColor, endColor};
    }

    @AutoProperty public int gradientRadius = 200;

    // width & height set here will be modified to 100 by the data binding's
    // SeekBar during initializing, so we init them again in Activity
    @AutoProperty public int width = 400;
    @AutoProperty public int height = 400;
    @AutoProperty public int solidColor = 0xFF2DCFCA;
    @AutoProperty public int strokeWidth = 0;
    @AutoProperty public int strokeColor = 0xFF24A5A1;
    @AutoProperty public int dashWidth = 0;
    @AutoProperty public int dashGap = 0;
}
