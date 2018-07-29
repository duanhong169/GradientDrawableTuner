package top.defaults.gradientdrawabletuner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.List;

import top.defaults.gradientdrawabletuner.db.DrawableSpec;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DrawableSpecChooser {

    private Context context;
    private List<DrawableSpec> drawableSpecs;

    DrawableSpecChooser(Context context, List<DrawableSpec> drawableSpecs) {
        this.context = context;
        this.drawableSpecs = drawableSpecs;
    }

    public void show(View parent, DrawableSpecChooserObserver observer) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        if (inflater == null) return;

        @SuppressLint("InflateParams")
        View layout = inflater.inflate(R.layout.popup_drawable_spec_chooser, null);

        final PopupWindow popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        layout.findViewById(R.id.cancel).setOnClickListener(v -> popupWindow.dismiss());

        if(Build.VERSION.SDK_INT >= 21){
            popupWindow.setElevation(10.0f);
        }

        RecyclerView drawableSpecsRecyclerView = layout.findViewById(R.id.drawableSpecsList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        drawableSpecsRecyclerView.setLayoutManager(layoutManager);
        DrawableSpecAdapter adapter = new DrawableSpecAdapter(drawableSpecs);
        adapter.setOnItemClickListener((v, position) -> {
            if (observer != null) {
                observer.onDrawableSpecChose(drawableSpecs.get(position));
            }
            popupWindow.dismiss();
        });
        drawableSpecsRecyclerView.setAdapter(adapter);

        popupWindow.setAnimationStyle(R.style.DrawableSpecChooserAnimation);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public interface DrawableSpecChooserObserver {
        void onDrawableSpecChose(DrawableSpec drawableSpec);
    }
}
