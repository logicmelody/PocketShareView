package com.dl.pocketshareview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by logicmelody on 2015/6/3.
 */
public class FloatingViewDialogFragment extends DialogFragment {

    public static final String KEY_DELETED_TASK_ID = "key_deleted_task_id";

    private ViewGroup mFloatingView;
    private Button mFloatingButton;

    private long mDeletedTaskId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mFloatingView = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.floating_view, null);
        mFloatingButton = (Button) mFloatingView.findViewById(R.id.floating_button);

        mDeletedTaskId = getArguments().getLong(KEY_DELETED_TASK_ID, -1L);

        builder.setView(mFloatingView);

        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        // Show fade in animation
    }
}
