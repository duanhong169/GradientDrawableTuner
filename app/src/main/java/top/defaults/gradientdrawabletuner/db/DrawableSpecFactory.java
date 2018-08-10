package top.defaults.gradientdrawabletuner.db;

import top.defaults.gradientdrawabletuner.DrawablePropertiesFactory;

public class DrawableSpecFactory {

    public static DrawableSpec tempSpec() {
        DrawablePropertiesInRoom properties = DrawablePropertiesFactory.createDefault();
        return new DrawableSpec("Untitled", properties);
    }

    public static DrawableSpec rectangleSample(String name) {
        DrawablePropertiesInRoom properties = DrawablePropertiesFactory.createRectangleSample();
        return new DrawableSpec(name, properties);
    }
}
