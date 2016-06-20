package com.github.kurtishu.gank.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.github.kurtishu.gank.R;

/**
 * Created by kurtishu on 6/20/16.
 */
public class InfoDialog extends DialogFragment {

   private static final String TITLE = "title";
   private static final String CONTENT = "content";

    private String mTitle;
    private CharSequence mContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        mTitle = b.getString(TITLE);
        mContent = b.getCharSequence(CONTENT);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView textView = (TextView)LayoutInflater.from(getActivity()).inflate(R.layout.dialog_text, null);
        textView.setText(mContent);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        builder.setTitle(mTitle).setView(textView).setPositiveButton(R.string.button_confirm, null);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public static InfoDialog newInstance(String title, CharSequence content) {
        InfoDialog dialog = new InfoDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putCharSequence(CONTENT, content);
        dialog.setArguments(bundle);
        return dialog;
    }
}
