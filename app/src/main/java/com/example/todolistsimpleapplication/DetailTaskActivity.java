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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class DetailTaskActivity extends AppCompatActivity {
    private TextView tv_task_name, tv_description;
    private ImageView img_more, img_delete;
    private LinearLayout ll_back_to_task;
    private Task task;
    private List<Task> taskList;
    private int task_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        instantiateObject();
        initUI();
        setListenerForWidgets();
    }


    private void instantiateObject() {
        task = (Task) getIntent().getSerializableExtra("task_object");
        taskList = (List<Task>) getIntent().getSerializableExtra("task_list_object");
        task_id = getIntent().getIntExtra("number_count_id", 0);
    }

    private void initUI() {
        tv_task_name = findViewById(R.id.tv_task_name);
        tv_description = findViewById(R.id.tv_description);
        img_more = findViewById(R.id.img_more);
        img_delete = findViewById(R.id.img_delete);
        ll_back_to_task = findViewById(R.id.ll_back_to_task);
        tv_task_name.setText(task.getName());
        String description = task.getDescription();
        if(description.isEmpty()) tv_description.setTextColor(ContextCompat.getColor(this, R.color.gray));
        else tv_description.setTextColor(ContextCompat.getColor(this, R.color.light_black));
        tv_description.setText(description.isEmpty() ? "Add Note" : task.getDescription());
    }

    private void setListenerForWidgets() {
        ll_back_to_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main_activity = new Intent(DetailTaskActivity.this, MainActivity.class);
                main_activity.putExtra("task_list_object", (Serializable) taskList);
                main_activity.putExtra("number_count_id", task_id);
                startActivity(main_activity);
            }
        });
        tv_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDescription();
            }
        });
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBottom();
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(task.getName());
            }
        });
    }
    private void showDialogDescription(){
        //Define dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_create_description);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        TextView dialog_note_task_name = dialog.findViewById(R.id.dialog_note_task_name);
        TextView tv_dialog_complete_note_btn = dialog.findViewById(R.id.tv_dialog_complete_note_btn);
        EditText et_add_description_task = dialog.findViewById(R.id.et_add_description_task);
        dialog_note_task_name.setText(task.getName());
        tv_dialog_complete_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String description = et_add_description_task.getText().toString().trim();
                task.setDescription(description);
                createToast("Text Note successes", R.drawable.baseline_check_circle_24);
                initUI();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showDialogBottom(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_bottom);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBottomAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        //signing variable
        TextView tv_update_btn = dialog.findViewById(R.id.tv_update_btn);
        TextView tv_delete_btn = dialog.findViewById(R.id.tv_delete_btn);
        tv_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialogChangeNameTask();
            }
        });
        tv_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showDialogDelete(task.getName());
            }
        });
    }

    private void showDialogDelete(String name){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog_delete);
        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        //Text View
        TextView tv_delete_name = dialog.findViewById(R.id.tv_delete_name);
        TextView tv_action_no = dialog.findViewById(R.id.tv_action_no);
        TextView tv_action_yes = dialog.findViewById(R.id.tv_action_yes);
        //Signing variable
        tv_delete_name.setText("Delete: " + name);
        //Set Listener for widgets
        tv_action_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_action_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                removeTaskById(task.getTask_id());
                createToast("Deleted " + name, R.drawable.baseline_check_circle_24);
                Intent main_activity = new Intent(DetailTaskActivity.this, MainActivity.class);
                main_activity.putExtra("task_list_object", (Serializable) taskList);
                main_activity.putExtra("number_count_id", task_id);
                startActivity(main_activity);
            }
        });
    }
    private void removeTaskById(int taskId) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getTask_id() == taskId) {
                taskList.remove(i);
                break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showDialogChangeNameTask(){
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

        tv_header_add.setText("Change name task");
        et_add_new_list_work.setHint("name task");
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
                createToast("Update name task successful", R.drawable.baseline_check_circle_24);
                task.setName(task_name);
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