package top.defaults.gradientdrawabletuner;

import android.graphics.Color;

import top.defaults.drawabletoolbox.DrawableProperties;
import top.defaults.gradientdrawabletuner.db.DrawablePropertiesInRoom;

class PropertiesExchange {

    static DrawablePropertiesInRoom toRoom(DrawableProperties properties) {
        DrawablePropertiesInRoom propertiesInRoom = new DrawablePropertiesInRoom();
        propertiesInRoom.shape = properties.shape;

        propertiesInRoom.innerRadius = properties.innerRadius;
        propertiesInRoom.innerRadiusRatio = properties.innerRadiusRatio;
        propertiesInRoom.thickness = properties.thickness;
        propertiesInRoom.thicknessRatio = properties.thicknessRatio;

        propertiesInRoom.setCornerRadius(properties.getCornerRadius());
        propertiesInRoom.topLeftRadius = properties.topLeftRadius;
        propertiesInRoom.topRightRadius = properties.topRightRadius;
        propertiesInRoom.bottomLeftRadius = properties.bottomLeftRadius;
        propertiesInRoom.bottomRightRadius = properties.bottomRightRadius;

        propertiesInRoom.type = properties.type;

        propertiesInRoom.useGradient = properties.useGradient;
        propertiesInRoom.useCenterColor = properties.useCenterColor;

        propertiesInRoom.angle = properties.angle;
        propertiesInRoom.centerX = properties.centerX;
        propertiesInRoom.centerY = properties.centerY;
        propertiesInRoom.startColor = properties.startColor;
        propertiesInRoom.centerColor = properties.centerColor == null ? Color.TRANSPARENT : properties.centerColor;
        propertiesInRoom.endColor = properties.endColor;

        propertiesInRoom.gradientRadiusType = properties.gradientRadiusType;
        propertiesInRoom.gradientRadius = properties.gradientRadius;

        propertiesInRoom.width = properties.width;
        propertiesInRoom.height = properties.height;
        propertiesInRoom.solidColor = properties.solidColor;
        propertiesInRoom.strokeWidth = properties.strokeWidth;
        propertiesInRoom.strokeColor = properties.strokeColor;
        propertiesInRoom.dashWidth = properties.dashWidth;
        propertiesInRoom.dashGap = properties.dashGap;
        return propertiesInRoom;
    }

    static DrawableProperties fromRoom(DrawablePropertiesInRoom propertiesInRoom) {
        DrawableProperties properties = new DrawableProperties();
        properties.shape = propertiesInRoom.shape;

        properties.innerRadius = propertiesInRoom.innerRadius;
        properties.innerRadiusRatio = propertiesInRoom.innerRadiusRatio;
        properties.thickness = propertiesInRoom.thickness;
        properties.thicknessRatio = propertiesInRoom.thicknessRatio;

        properties.setCornerRadius(propertiesInRoom.getCornerRadius());
        properties.topLeftRadius = propertiesInRoom.topLeftRadius;
        properties.topRightRadius = propertiesInRoom.topRightRadius;
        properties.bottomLeftRadius = propertiesInRoom.bottomLeftRadius;
        properties.bottomRightRadius = propertiesInRoom.bottomRightRadius;

        properties.type = propertiesInRoom.type;

        properties.useGradient = propertiesInRoom.useGradient;
        properties.useCenterColor = propertiesInRoom.useCenterColor;

        properties.angle = propertiesInRoom.angle;
        properties.centerX = propertiesInRoom.centerX;
        properties.centerY = propertiesInRoom.centerY;
        properties.startColor = propertiesInRoom.startColor;
        properties.centerColor = propertiesInRoom.centerColor;
        properties.endColor = propertiesInRoom.endColor;

        properties.gradientRadiusType = propertiesInRoom.gradientRadiusType;
        properties.gradientRadius = propertiesInRoom.gradientRadius;

        properties.width = propertiesInRoom.width;
        properties.height = propertiesInRoom.height;
        properties.solidColor = propertiesInRoom.solidColor;
        properties.strokeWidth = propertiesInRoom.strokeWidth;
        properties.strokeColor = propertiesInRoom.strokeColor;
        properties.dashWidth = propertiesInRoom.dashWidth;
        properties.dashGap = propertiesInRoom.dashGap;
        return properties;
    }
}
