package top.defaults.gradientdrawabletuner.db;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

import top.defaults.gradientdrawabletuner.R;

import static top.defaults.drawabletoolbox.DrawableProperties.RADIUS_TYPE_PIXELS;

public class DrawablePropertiesInRoom implements Parcelable {

    public int shape = GradientDrawable.RECTANGLE;

    // all dimens are in pixels
    public int innerRadius = -1; // when innerRadius == -1, innerRadiusRatio become effective
    public float innerRadiusRatio = 9f;
    public int thickness = -1; // when thickness == -1, thicknessRatio become effective
    public float thicknessRatio = 3f;

    private int cornerRadius = 0; // This is overridden for each corner by the following 4 properties

    public DrawablePropertiesInRoom() {}

    public DrawablePropertiesInRoom copy() {
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        DrawablePropertiesInRoom properties = DrawablePropertiesInRoom.CREATOR.createFromParcel(parcel);
        parcel.recycle();
        return properties;
    }

    private DrawablePropertiesInRoom(Parcel in) {
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

    public static final Creator<DrawablePropertiesInRoom> CREATOR = new Creator<DrawablePropertiesInRoom>() {
        @Override
        public DrawablePropertiesInRoom createFromParcel(Parcel in) {
            return new DrawablePropertiesInRoom(in);
        }

        @Override
        public DrawablePropertiesInRoom[] newArray(int size) {
            return new DrawablePropertiesInRoom[size];
        }
    };

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
    public int topLeftRadius = 0;
    public int topRightRadius = 0;
    public int bottomLeftRadius = 0;
    public int bottomRightRadius = 0;

    public int type = GradientDrawable.RADIAL_GRADIENT;

    public boolean useGradient = false;
    public boolean useCenterColor = true;

    public int angle = 0;
    public float centerX = 0.5f;
    public float centerY = 0.5f;
    public int startColor = 0xFF2DCFCA;
    public int centerColor = Color.TRANSPARENT;
    public int endColor = 0x7FFFFFFF;

    public int gradientRadiusType = RADIUS_TYPE_PIXELS;
    public float gradientRadius = 200;

    public int width = 400;
    public int height = 400;
    public int solidColor = 0xFF2DCFCA;
    public int strokeWidth = 0;
    public int strokeColor = 0xFF24A5A1;
    public int dashWidth = 0;
    public int dashGap = 0;

    public boolean shouldEnableGradient() {
        return useGradient && shape != GradientDrawable.LINE;
    }

    public boolean shouldEnableCenterColor() {
        return useCenterColor && shouldEnableGradient();
    }

    public boolean shouldEnableGradientRadius() {
        return type == GradientDrawable.RADIAL_GRADIENT && shouldEnableGradient();
    }

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
    public int getShapeId() {
        return shapeToIdMap.get(shape);
    }
    public void setShapeId(Integer id) {
        shape = idToShapeMap.get(id);
    }

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
    public int getTypeId() {
        return typeToIdMap.get(type);
    }
    public void setTypeId(Integer id) {
        type = idToTypeMap.get(id);
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
}
