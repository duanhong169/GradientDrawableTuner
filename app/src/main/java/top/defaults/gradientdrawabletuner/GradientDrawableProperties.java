package top.defaults.gradientdrawabletuner;

import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

import top.defaults.annotations.AutoProperty;

public class GradientDrawableProperties implements Parcelable {

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

    private GradientDrawableProperties() {}

    private GradientDrawableProperties(Parcel in) {
        shape = in.readInt();
        innerRadius = in.readInt();
        innerRadiusRatio = in.readFloat();
        thickness = in.readInt();
        thicknessRatio = in.readFloat();
        cornerRadius = in.readInt();
        topLeftRadius = in.readInt();
        topRightRadius = in.readInt();
        bottomLeftRadius = in.readInt();
        bottomRightRadius = in.readInt();
        type = in.readInt();
        useGradient = in.readByte() != 0;
        useCenterColor = in.readByte() != 0;
        angle = in.readInt();
        centerX = in.readFloat();
        centerY = in.readFloat();
        startColor = in.readInt();
        centerColor = in.readInt();
        endColor = in.readInt();
        gradientRadiusType = in.readInt();
        gradientRadius = in.readFloat();
        width = in.readInt();
        height = in.readInt();
        solidColor = in.readInt();
        strokeWidth = in.readInt();
        strokeColor = in.readInt();
        dashWidth = in.readInt();
        dashGap = in.readInt();
    }

    public static final Creator<GradientDrawableProperties> CREATOR = new Creator<GradientDrawableProperties>() {
        @Override
        public GradientDrawableProperties createFromParcel(Parcel in) {
            return new GradientDrawableProperties(in);
        }

        @Override
        public GradientDrawableProperties[] newArray(int size) {
            return new GradientDrawableProperties[size];
        }
    };

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

    static final int RADIUS_TYPE_PIXELS = 0;
    static final int RADIUS_TYPE_FRACTION = 1;
    @AutoProperty public int gradientRadiusType = RADIUS_TYPE_PIXELS;
    @AutoProperty("gradientRadius") private float gradientRadius = 200;

    public int getGradientRadiusInt() {
        return Math.round(gradientRadius);
    }

    public float getGradientRadius() {
        return gradientRadius;
    }

    public void setGradientRadius(Float gradientRadius) {
        this.gradientRadius = gradientRadius;
    }

    // width & height set here will be modified to 100 by the data binding's
    // SeekBar during initializing, so we init them again in Activity
    @AutoProperty public int width = 400;
    @AutoProperty public int height = 400;
    @AutoProperty public int solidColor = 0xFF2DCFCA;
    @AutoProperty public int strokeWidth = 0;
    @AutoProperty public int strokeColor = 0xFF24A5A1;
    @AutoProperty public int dashWidth = 0;
    @AutoProperty public int dashGap = 0;

    public boolean shouldEnableGradient() {
        return useGradient && shape != GradientDrawable.LINE;
    }

    public boolean shouldEnableCenterColor() {
        return useCenterColor && shouldEnableGradient();
    }

    public boolean shouldEnableGradientRadius() {
        return type == GradientDrawable.RADIAL_GRADIENT && shouldEnableGradient();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shape);
        dest.writeInt(innerRadius);
        dest.writeFloat(innerRadiusRatio);
        dest.writeInt(thickness);
        dest.writeFloat(thicknessRatio);
        dest.writeInt(cornerRadius);
        dest.writeInt(topLeftRadius);
        dest.writeInt(topRightRadius);
        dest.writeInt(bottomLeftRadius);
        dest.writeInt(bottomRightRadius);
        dest.writeInt(type);
        dest.writeByte((byte) (useGradient ? 1 : 0));
        dest.writeByte((byte) (useCenterColor ? 1 : 0));
        dest.writeInt(angle);
        dest.writeFloat(centerX);
        dest.writeFloat(centerY);
        dest.writeInt(startColor);
        dest.writeInt(centerColor);
        dest.writeInt(endColor);
        dest.writeInt(gradientRadiusType);
        dest.writeFloat(gradientRadius);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(solidColor);
        dest.writeInt(strokeWidth);
        dest.writeInt(strokeColor);
        dest.writeInt(dashWidth);
        dest.writeInt(dashGap);
    }

    public static class Factory {

        public static GradientDrawableProperties createDefault() {
            return new GradientDrawableProperties();
        }

        public static GradientDrawableProperties createRectangleSample() {
            GradientDrawableProperties properties = new GradientDrawableProperties();
            properties.shape = GradientDrawable.RECTANGLE;
            properties.topLeftRadius = 60;
            properties.topRightRadius = 60;
            properties.useGradient = true;
            properties.type = GradientDrawable.RADIAL_GRADIENT;
            properties.setGradientRadius(520f);
            properties.centerX = 0.5f;
            properties.centerY = 1.0f;
            properties.strokeWidth = 4;
            properties.dashWidth = 20;
            properties.dashGap = 12;
            return properties;
        }
    }
}
