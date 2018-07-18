package top.defaults.gradientdrawabletuner;

import android.graphics.Color;
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

    // all dimens are in dp
    @AutoProperty public int innerRadius = 0;
    @AutoProperty public float innerRadiusRatio = 0;
    @AutoProperty public int thickness = 0;
    @AutoProperty public float thicknessRatio = 0;
    @AutoProperty public int cornerRadius = 0;
    @AutoProperty public int width = 100;
    @AutoProperty public int height = 100;
    @AutoProperty public int solidColor = Color.WHITE;
    @AutoProperty public int strokeWidth = 1;
    @AutoProperty public int strokeColor = Color.RED;
}
