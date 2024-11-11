package com.example.todolistsimpleapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcv_task_list;
    private CardView cv_add_new_list;
    private TaskAdapter taskAdapter;
    //Objects
    private List<Task> taskList = new ArrayList<>();
    private int task_id = 0;

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void stateTaskList() {
        List<Task> taskListSave = (List<Task>) getIntent().getSerializableExtra("task_list_object");
        int task_id_save = getIntent().getIntExtra("number_count_id", 0);
        if (taskListSave == null) return;
        taskList.addAll(taskListSave);
        task_id = task_id_save;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        instantiateObject();
        stateTaskList();
        initUI();
        setListenerForWidgets();
    }

    private void instantiateObject() {
//        taskList = new ArrayList<>();
    }


    private void initUI() {
        cv_add_new_list = findViewById(R.id.cv_add_new_list);
        rcv_task_list = findViewById(R.id.rcv_task_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rcv_task_list.setLayoutManager(linearLayoutManager);
        taskAdapter = new TaskAdapter(taskList, new IClickItemListener() {
            @Override
            public void onClickItem(Object object) {
                Intent detail_task_activity = new Intent(MainActivity.this, DetailTaskActivity.class);
                detail_task_activity.putExtra("task_object",(Task) object);
                detail_task_activity.putExtra("task_list_object", (Serializable) taskList);
                detail_task_activity.putExtra("number_count_id", task_id);
                startActivity(detail_task_activity);
            }
        });
        rcv_task_list.setAdapter(taskAdapter);

    }
    private void setListenerForWidgets() {
        cv_add_new_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddNewTask();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void showDialogAddNewTask(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_add_new);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);

        //Widgets
        TextView tv_header_add = dialog.findViewById(R.id.tv_header_add);
        EditText et_add_new_list_work = dialog.findViewById(R.id.et_add_new);
        Button btn_add_new_list_work = dialog.findViewById(R.id.btn_add_new);
        Button btn_cancel_dialog = dialog.findViewById(R.id.btn_cancel_dialog);

        tv_header_add.setText("Add new Task");
        et_add_new_list_work.setHint("New Task");
        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_add_new_list_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String task_name = et_add_new_list_work.getText().toString();
                if(task_name.trim().isEmpty()) {
                    createToast("Empty name task", R.drawable.baseline_warning_24);
                    return;
                }
                for (Task task: taskList) {
                    if (task.getName().equals(task_name.trim())) {
                        createToast("Duplicate name task", R.drawable.baseline_warning_24);
                        return;
                    }
                }
                createToast("Create task successful", R.drawable.baseline_check_circle_24);
                taskList.add(new Task(task_id, task_name, ""));
                task_id++;
                initUI();
            }
        });

    }
    private void createToast(String input_text_to_toast, int imageResId) {
        Toast toast = new Toast(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_toast, findViewById(R.id.layout_custom_toast));
        TextView text_toast = view.findViewById(R.id.text_toast);
        ImageView img_icon_toast = view.findViewById(R.id.img_icon_toast);
        text_toast.setText(input_text_to_toast);
        img_icon_toast.setImageResource(imageResId);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}