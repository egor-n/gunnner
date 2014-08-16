package com.egorn.dribbble.ui.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.egorn.dribbble.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Egor N.
 */
public class InputDialog extends AlertDialog {
    @InjectView(R.id.dialog_custom_content) EditText mDialogCustomContent;

    private CustomDialogCallback callback;

    public InputDialog(Context context, CustomDialogCallback callback) {
        super(context);
        this.callback = callback;
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        setView(root);
        ButterKnife.inject(this, root);
    }

    @OnClick(R.id.dialog_custom_cancel)
    void onCancel() {
        if (callback != null) {
            callback.onCancel();
        }
        dismiss();
    }

    @OnClick(R.id.dialog_custom_confirm)
    void onConfirm() {
        if (TextUtils.isEmpty(mDialogCustomContent.getText().toString().trim())) {
            return;
        }

        if (callback != null) {
            callback.onConfirm(mDialogCustomContent.getText().toString().trim());
        }
        dismiss();
    }

    public interface CustomDialogCallback {
        void onConfirm(String username);

        void onCancel();
    }
}
