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

public class AddTaskDialogFragment extends DialogFragment {

    private EditText taskTitleInput;
    private EditText taskDescriptionInput;
    private Button saveTaskButton;

    public static AddTaskDialogFragment newInstance() {
        return new AddTaskDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_task, container, false);

        taskTitleInput = view.findViewById(R.id.task_title_input);
        taskDescriptionInput = view.findViewById(R.id.task_description_input);
        saveTaskButton = view.findViewById(R.id.save_task_button);

        saveTaskButton.setOnClickListener(v -> {
            String title = taskTitleInput.getText().toString();
            String description = taskDescriptionInput.getText().toString();

            if (!title.isEmpty()) {
                TaskRepository.addTask(title, description);
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