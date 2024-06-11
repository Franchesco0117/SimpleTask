package com.francisco.simpletask;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.francisco.simpletask.data.TaskRepository;
import com.francisco.simpletask.models.Task;
import com.francisco.simpletask.ui.AddTaskDialogFragment;
import com.francisco.simpletask.ui.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView taskRecyclerView = findViewById(R.id.task_recycler_view);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Task> tasks = TaskRepository.getTasks();
        taskAdapter = new TaskAdapter(this, tasks);
        taskRecyclerView.setAdapter(taskAdapter);

        FloatingActionButton fab = findViewById(R.id.add_task_button);
        fab.setOnClickListener(view -> {
            AddTaskDialogFragment dialogFragment = AddTaskDialogFragment.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "AddTaskDialogFragment");
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Task task = tasks.get(position);

                // Verificar si la tarea tiene subtareas
                if (!task.getSubtasks().isEmpty()) {
                    // Eliminar la primera subtarea como ejemplo
                    Task subtask = task.getSubtasks().get(0);
                    taskAdapter.removeSubtask(task, subtask);
                } else {
                    // Eliminar la tarea principal si no tiene subtareas
                    TaskRepository.removeTask(task);
                    taskAdapter.notifyItemRemoved(position);
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);
    }

    public void refreshTaskList() {
        taskAdapter.notifyDataSetChanged();
    }
}