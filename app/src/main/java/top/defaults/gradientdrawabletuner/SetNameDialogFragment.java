package top.defaults.gradientdrawabletuner;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class SetNameDialogFragment extends DialogFragment {

    public interface Callback {
        void onSet(String name);
    }

    private Callback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_set_name, container, false);
        final EditText nameEditText = view.findViewById(R.id.name);
        final View ok = view.findViewById(R.id.ok);
        final View cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> dismiss());
        ok.setOnClickListener(v -> {
            if (callback != null) {
                String name = nameEditText.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    callback.onSet(name);
                }
            }
            dismiss();
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        if (window != null) {
            Display display = window.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = (int) (size.x * 0.75);
            window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
