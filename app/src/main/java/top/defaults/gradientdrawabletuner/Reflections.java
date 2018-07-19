package top.defaults.gradientdrawabletuner;

import android.graphics.drawable.GradientDrawable;

import java.lang.reflect.Field;

class Reflections {
    
    private static Class<?> gradientState = resolveGradientState();

    private static Class<?> resolveGradientState() {
        Class<?>[] classes = GradientDrawable.class.getDeclaredClasses();
        for (Class<?> singleClass : classes) {
            if (singleClass.getSimpleName().equals("GradientState")) return singleClass;
        }
        throw new RuntimeException("GradientState could not be found in current GradientDrawable implementation");
    }

    private static Field resolveField(Class<?> source, String fieldName) throws SecurityException, NoSuchFieldException {
        Field field = source.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    static void setInnerRadius(GradientDrawable drawable, int value) {
        try {
            Field innerRadius = resolveField(gradientState, "mInnerRadius");
            innerRadius.setInt(drawable.getConstantState(), value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void setInnerRadiusRatio(GradientDrawable drawable, float value) {
        try {
            Field innerRadius = resolveField(gradientState, "mInnerRadiusRatio");
            innerRadius.setFloat(drawable.getConstantState(), value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void setThickness(GradientDrawable drawable, int value) {
        try {
            Field innerRadius = resolveField(gradientState, "mThickness");
            innerRadius.setInt(drawable.getConstantState(), value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void setThicknessRatio(GradientDrawable drawable, float value) {
        try {
            Field innerRadius = resolveField(gradientState, "mThicknessRatio");
            innerRadius.setFloat(drawable.getConstantState(), value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void setUseLevelForShape(GradientDrawable drawable, boolean value) {
        try {
            Field useLevelForShape = resolveField(gradientState, "mUseLevelForShape");
            useLevelForShape.setBoolean(drawable.getConstantState(), value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
