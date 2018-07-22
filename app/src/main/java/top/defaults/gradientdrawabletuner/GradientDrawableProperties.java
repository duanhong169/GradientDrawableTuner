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
    @AutoProperty public int paddingLeft = 0;
    @AutoProperty public int paddingTop = 0;
    @AutoProperty public int paddingRight = 0;
    @AutoProperty public int paddingBottom = 0;
    // width & height set here will be modified to 100 by the data binding's
    // SeekBar during initializing, so we set 0 here and init them in Activity
    @AutoProperty public int width = 0;
    @AutoProperty public int height = 0;
    @AutoProperty public int solidColor = 0xFF2DCFCA;
    @AutoProperty public int strokeWidth = 0;
    @AutoProperty public int strokeColor = 0xFF24A5A1;
    @AutoProperty public int dashWidth = 0;
    @AutoProperty public int dashGap = 0;
}
