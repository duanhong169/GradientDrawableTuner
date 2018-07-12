package top.defaults.gradientdrawabletuner;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ColorPickerPopup {

    private Context context;

    public ColorPickerPopup(Context context) {
        this.context = context;
    }

    public void show(View anchor) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        if (inflater == null) return;

        View customView = inflater.inflate(R.layout.color_picker_popup, null);
        PopupWindow popupWindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        if(Build.VERSION.SDK_INT >= 21){
            popupWindow.setElevation(5.0f);
        }

        popupWindow.showAsDropDown(anchor);
    }
}
