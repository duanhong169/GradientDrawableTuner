package top.defaults.gradientdrawabletuner.db;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "drawable_spec")
public class DrawableSpec {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    @Embedded
    private DrawablePropertiesInRoom properties;

    DrawableSpec(String name, DrawablePropertiesInRoom properties) {
        this.name = name;
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DrawablePropertiesInRoom getProperties() {
        return properties;
    }

    public void setProperties(DrawablePropertiesInRoom propertiesInRoom) {
        this.properties = propertiesInRoom;
    }

    public static DrawableSpec[] populateData() {
        return new DrawableSpec[]{
                DrawableSpecFactory.rectangleSample("Rectangle"),
        };
    }
}
