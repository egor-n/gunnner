package com.gunnner.ui.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.gunnner.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Egor N.
 */
public class InputDialog extends AlertDialog implements TextView.OnEditorActionListener {
    @InjectView(R.id.dialog_custom_content) EditText mContent;

    private CustomDialogCallback callback;
    private int type;

    public InputDialog(Context context, int type, CustomDialogCallback callback) {
        super(context);
        this.type = type;
        this.callback = callback;
        init(context);
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        setView(root);
        ButterKnife.inject(this, root);

        mContent.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mContent.setOnEditorActionListener(this);
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
        if (TextUtils.isEmpty(mContent.getText().toString().trim())) {
            return;
        }

        if (callback != null) {
            callback.onConfirm(mContent.getText().toString().trim(), type);
        }
        dismiss();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            onConfirm();
            return true;
        }
        return false;
    }

    public interface CustomDialogCallback {
        void onConfirm(String username, int type);

        void onCancel();
    }
}
