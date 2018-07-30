package top.defaults.gradientdrawabletuner;

import android.app.Application;

import top.defaults.view.TextButton;
import top.defaults.view.TextButtonEffect;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ShapeXmlGenerator.init(this);
        TextButton.Defaults defaults = TextButton.Defaults.get();
        defaults.set(top.defaults.view.textbutton.R.styleable.TextButton_backgroundEffect, TextButtonEffect.BACKGROUND_EFFECT_RIPPLE);
    }
}
