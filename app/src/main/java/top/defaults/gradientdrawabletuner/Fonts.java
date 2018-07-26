package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class Fonts {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();
    private static final String DEFAULT_FONT = "fonts/RobotoMono-Regular.ttf";

    public static Typeface getDefault(Context context) {
        return get(DEFAULT_FONT, context);
    }

    private static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
