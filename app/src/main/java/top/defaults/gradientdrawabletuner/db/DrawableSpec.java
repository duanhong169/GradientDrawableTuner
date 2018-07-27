package top.defaults.gradientdrawabletuner.db;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import top.defaults.gradientdrawabletuner.GradientDrawableProperties;

@Entity(tableName = "drawable_spec")
public class DrawableSpec {

    @PrimaryKey
    private int id;

    private String name;

    @Embedded
    private GradientDrawableProperties properties;

    DrawableSpec(String name, GradientDrawableProperties properties) {
        this.name = name;
        this.properties = properties;
    }

    public static DrawableSpec[] populateData() {
        return new DrawableSpec[]{ DrawableSpecFactory.rectangleSample("Rectangle 1") };
    }
}
