package top.defaults.gradientdrawabletuner;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseIntArray;

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

    public int shape = GradientDrawable.OVAL;
    public int getShapeId() {
        return shapeToIdMap.get(shape);
    }
    public void setShapeById(int id) {
        shape = idToShapeMap.get(id);
    }

    public int solidColor = Color.WHITE;
    public int radius = 0; // in dp

    public int strokeWidth = 1;
    public int strokeColor = Color.RED;
}
