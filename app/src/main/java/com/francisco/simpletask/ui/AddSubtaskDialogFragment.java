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

public class AddSubtaskDialogFragment extends DialogFragment {

    private static final String ARG_TASK_ID = "task_id";
    private int taskId;

    public static AddSubtaskDialogFragment newInstance(int taskId) {
        AddSubtaskDialogFragment fragment = new AddSubtaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_subtask, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        taskId = getArguments().getInt(ARG_TASK_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_subtask, null);

        EditText subtaskTitleEditText = view.findViewById(R.id.subtask_title_input);

        builder.setView(view)
                .setPositiveButton(R.string.btn_add_subtask, (dialog, which) -> {
                    String subtaskTitle = subtaskTitleEditText.getText().toString();
                    TaskRepository.addSubtask(TaskRepository.getTaskById(taskId), subtaskTitle);
                    ((MainActivity) getActivity()).refreshTaskList();
                })
                .setNegativeButton(R.string.btn_cancel_subtask, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}