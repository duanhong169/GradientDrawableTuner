package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import static top.defaults.gradientdrawabletuner.GradientDrawableProperties.RADIUS_TYPE_PIXELS;

public class ShapeXmlGenerator {

    private static float density = 1.f;

    public static void init(Context context) {
        density = context.getResources().getDisplayMetrics().density;
    }

    public static String shapeXmlString(@Nullable GradientDrawableProperties properties) {
        if (properties == null) {
            return "Null properties";
        }
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("shape")
                    .addAttribute("xmlns:android", "http://schemas.android.com/apk/res/android")
                    .addAttribute("android:shape", nameForShape(properties.shape));
            if (hasRadius(properties)) {
                Element corners = root.addElement("corners");
                if (isCornerRadiusEven(properties)) {
                    corners.addAttribute("android:radius", stringOf(properties.getCornerRadius()));
                } else {
                    if (properties.topLeftRadius > 0) {
                        corners.addAttribute("android:topLeftRadius", stringOf(properties.topLeftRadius));
                    }
                    if (properties.topRightRadius > 0) {
                        corners.addAttribute("android:topRightRadius", stringOf(properties.topRightRadius));
                    }
                    if (properties.bottomLeftRadius > 0) {
                        corners.addAttribute("android:bottomLeftRadius", stringOf(properties.bottomLeftRadius));
                    }
                    if (properties.bottomRightRadius > 0) {
                        corners.addAttribute("android:bottomRightRadius", stringOf(properties.bottomRightRadius));
                    }
                }
            }

            if (properties.shouldEnableGradient()) {
                Element gradient = root.addElement("gradient");
                gradient.addAttribute("android:type", nameForGradientType(properties.type));

                if (properties.angle > 0) {
                    gradient.addAttribute("android:angle", String.valueOf(properties.angle));
                }

                if (properties.type != GradientDrawable.LINEAR_GRADIENT) {
                    if (properties.centerX != 0.5f) {
                        gradient.addAttribute("android:centerX", String.valueOf(properties.centerX));
                    }

                    if (properties.centerY != 0.5f) {
                        gradient.addAttribute("android:centerY", String.valueOf(properties.centerY));
                    }
                }

                if (properties.type == GradientDrawable.RADIAL_GRADIENT) {
                    if (properties.gradientRadiusType == RADIUS_TYPE_PIXELS) {
                        gradient.addAttribute("android:gradientRadius", stringOf(properties.getGradientRadiusInt()));
                    }
                }

                gradient.addAttribute("android:startColor", colorHex(properties.startColor));
                gradient.addAttribute("android:endColor", colorHex(properties.endColor));

                if (properties.useCenterColor) {
                    gradient.addAttribute("android:centerColor", colorHex(properties.centerColor));
                }
            }

            if (properties.width > 0 || properties.height > 0) {
                Element size = root.addElement("size");
                if (properties.width > 0) {
                    size.addAttribute("android:width", stringOf(properties.width));
                }
                if (properties.height > 0) {
                    size.addAttribute("android:height", stringOf(properties.height));
                }
            }

            if (!properties.shouldEnableGradient()) {
                Element solid = root.addElement("solid");
                solid.addAttribute("android:color", colorHex(properties.solidColor));
            }

            if (properties.strokeWidth > 0) {
                Element stroke = root.addElement("stroke");
                stroke.addAttribute("android:width", stringOf(properties.strokeWidth));
                stroke.addAttribute("android:color", colorHex(properties.strokeColor));

                if (properties.dashWidth > 0 && properties.dashGap > 0) {
                    stroke.addAttribute("android:dashWidth", stringOf(properties.dashWidth));
                    stroke.addAttribute("android:dashGap", stringOf(properties.dashGap));
                }
            }

            return prettyPrint(document);
        } catch (IllegalArgumentException e) {
            return "Invalid properties";
        }
    }

    private static String nameForShape(int shape) {
        switch (shape) {
            case GradientDrawable.RECTANGLE:
                return "rectangle";
            case GradientDrawable.OVAL:
                return "oval";
            case GradientDrawable.LINE:
                return "line";
            case GradientDrawable.RING:
                return "ring";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String nameForGradientType(int shape) {
        switch (shape) {
            case GradientDrawable.LINEAR_GRADIENT:
                return "linear";
            case GradientDrawable.RADIAL_GRADIENT:
                return "radial";
            case GradientDrawable.SWEEP_GRADIENT:
                return "sweep";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static boolean hasRadius(GradientDrawableProperties properties) {
        return !(properties.getCornerRadius() == 0 && isCornerRadiusEven(properties));
    }

    private static boolean isCornerRadiusEven(GradientDrawableProperties properties) {
        int cornerRadius = properties.getCornerRadius();
        return cornerRadius == properties.topLeftRadius &&
                cornerRadius == properties.topRightRadius &&
                cornerRadius == properties.bottomLeftRadius &&
                cornerRadius == properties.bottomRightRadius;
    }

    private static String stringOf(int pixel) {
        if (pixel == 1) return "1px";
        return Math.round(pixel / density) + "dp";
    }

    private static String prettyPrint(Document document) {
        StringWriter sw = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setIndentSize(4);
        XMLWriter xw = new CustomXMLWriter(sw, format);
        try {
            xw.write(document);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return sw.toString();
    }

    private static String colorHex(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return String.format(Locale.getDefault(), "0x%02X%02X%02X%02X", a, r, g, b);
    }
}
