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
import androidx.fragment.app.DialogFragment;

import com.francisco.simpletask.MainActivity;
import com.francisco.simpletask.R;
import com.francisco.simpletask.data.TaskRepository;
import com.francisco.simpletask.models.Task;

public class EditTaskDialogFragment extends DialogFragment {

    private static final String ARG_TASK_ID = "taskId";
    private EditText taskTitleInput;
    private EditText taskDescriptionInput;
    private Button saveTaskButton;
    private Task task;

    public static EditTaskDialogFragment newInstance(int taskId) {
        EditTaskDialogFragment fragment = new EditTaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_task, container, false);

        taskTitleInput = view.findViewById(R.id.task_title_input);
        taskDescriptionInput = view.findViewById(R.id.task_description_input);
        saveTaskButton = view.findViewById(R.id.save_task_button);

        if (getArguments() != null) {
            int taskId = getArguments().getInt(ARG_TASK_ID);
            task = TaskRepository.getTaskById(taskId);
            if (task != null) {
                taskTitleInput.setText(task.getTitle());
                taskDescriptionInput.setText(task.getDescription());
            }
        }

        saveTaskButton.setOnClickListener(v -> {
            String title = taskTitleInput.getText().toString();
            String description = taskDescriptionInput.getText().toString();

            if (task != null && !title.isEmpty()) {
                TaskRepository.updateTask(task, title, description);
                dismiss();
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).refreshTaskList();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}