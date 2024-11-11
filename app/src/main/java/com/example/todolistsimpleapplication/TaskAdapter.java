package com.example.todolistsimpleapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private IClickItemListener iClickItemListener;

    public TaskAdapter(List<Task> taskList, IClickItemListener iClickItemListener) {
        this.taskList = taskList;
        this.iClickItemListener = iClickItemListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tv_task_name.setText(task.getName());
        holder.card_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListener.onClickItem(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(taskList != null) {
            return taskList.size();
        }
        return 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_task_name;
        private CardView card_task;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            card_task = itemView.findViewById(R.id.card_task);
            tv_task_name = itemView.findViewById(R.id.tv_task_name);
        }
    }
}
