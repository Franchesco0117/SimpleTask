package com.francisco.simpletask.ui;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.francisco.simpletask.MainActivity;
import com.francisco.simpletask.R;
import com.francisco.simpletask.data.TaskRepository;
import com.francisco.simpletask.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final Context context;
    private final List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void removeSubtask(Task task, Task subtask) {
        task.getSubtasks().remove(subtask);
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle;
        TextView taskDescription;
        ViewGroup subtasksContainer;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.task_title);
            taskDescription = itemView.findViewById(R.id.task_description);
            subtasksContainer = itemView.findViewById(R.id.subtasks_container);
        }

        void bind(Task task) {
            taskTitle.setText(task.getTitle());
            taskDescription.setText(task.getDescription());

            // Botón para editar tarea
            ImageButton editButton = itemView.findViewById(R.id.edit_task_button);
            editButton.setOnClickListener(v -> {
                EditTaskDialogFragment dialogFragment = EditTaskDialogFragment.newInstance(task.getId());
                dialogFragment.show(((MainActivity) context).getSupportFragmentManager(), "EditTaskDialogFragment");
            });

            // Botón para eliminar tarea
            ImageButton deleteButton = itemView.findViewById(R.id.delete_task_button);
            deleteButton.setOnClickListener(v -> {
                TaskRepository.removeTask(task);
                notifyDataSetChanged();
            });

            // Botón para añadir subtarea
            LinearLayout addSubtaskButton = itemView.findViewById(R.id.add_subtask_button);
            addSubtaskButton.setOnClickListener(v -> {
                AddSubtaskDialogFragment dialogFragment = AddSubtaskDialogFragment.newInstance(task.getId());
                dialogFragment.show(((MainActivity) context).getSupportFragmentManager(), "AddSubtaskDialogFragment");
            });

            subtasksContainer.removeAllViews();

            for (Task subtask : task.getSubtasks()) {
                View subtaskView = LayoutInflater.from(context).inflate(R.layout.subtask_item, subtasksContainer, false);
                CheckBox subtaskCheckBox = subtaskView.findViewById(R.id.checkbox_subtask);

                subtaskCheckBox.setText(subtask.getTitle());
                subtaskCheckBox.setChecked(subtask.isCompleted());
                subtaskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    subtask.setCompleted(isChecked);
                });

                // Botón para editar subtarea
                ImageButton editSubtaskButton = subtaskView.findViewById(R.id.edit_subtask_button);
                editSubtaskButton.setOnClickListener(v -> {
                    EditSubtaskDialogFragment dialogFragment = EditSubtaskDialogFragment.newInstance(task.getId(), subtask.getId());
                    dialogFragment.show(((MainActivity) context).getSupportFragmentManager(), "EditSubtaskDialogFragment");
                });

                // Botón para eliminar subtarea
                ImageButton deleteSubtaskButton = subtaskView.findViewById(R.id.delete_subtask_button);
                deleteSubtaskButton.setOnClickListener(v -> {
                    TaskRepository.removeSubtask(task, subtask);
                    notifyDataSetChanged();
                });

                subtasksContainer.addView(subtaskView);
            }
        }
    }
}