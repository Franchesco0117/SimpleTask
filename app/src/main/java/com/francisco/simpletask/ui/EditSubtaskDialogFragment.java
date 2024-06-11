package com.francisco.simpletask.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.francisco.simpletask.MainActivity;
import com.francisco.simpletask.R;
import com.francisco.simpletask.data.TaskRepository;
import com.francisco.simpletask.models.Task;

public class EditSubtaskDialogFragment extends DialogFragment {

    private static final String ARG_TASK_ID = "task_id";
    private static final String ARG_SUBTASK_ID = "subtask_id";
    private int taskId;
    private int subtaskId;

    public static EditSubtaskDialogFragment newInstance(int taskId, int subtaskId) {
        EditSubtaskDialogFragment fragment = new EditSubtaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskId);
        args.putInt(ARG_SUBTASK_ID, subtaskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_subtask, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        taskId = getArguments().getInt(ARG_TASK_ID);
        subtaskId = getArguments().getInt(ARG_SUBTASK_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_subtask, null);

        EditText subtaskTitleEditText = view.findViewById(R.id.subtask_title_input);

        Task subtask = TaskRepository.getSubtaskById(taskId, subtaskId);
        subtaskTitleEditText.setText(subtask.getTitle());

        builder.setView(view)
                .setPositiveButton(R.string.btn_save_subtask, (dialog, which) -> {
                    String subtaskTitle = subtaskTitleEditText.getText().toString();
                    subtask.setTitle(subtaskTitle);
                    ((MainActivity) getActivity()).refreshTaskList();
                })
                .setNegativeButton(R.string.btn_cancel_subtask, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}