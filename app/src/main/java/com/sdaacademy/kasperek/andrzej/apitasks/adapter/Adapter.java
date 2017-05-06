package com.sdaacademy.kasperek.andrzej.apitasks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sdaacademy.kasperek.andrzej.apitasks.R;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper.Mapper;
import com.sdaacademy.kasperek.andrzej.apitasks.model.Task;
import com.sdaacademy.kasperek.andrzej.apitasks.service.TaskService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RENT on 2017-04-29.
 */

public class Adapter extends ArrayAdapter<Task> {

    private TaskService taskService;

    public Adapter(Context context, int resource, List<Task> objects, TaskService taskService) {
        super(context, 0, objects);
        this.taskService = taskService;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_row, parent, false);

            CheckBox complete = (CheckBox) convertView.findViewById(R.id.checkBoxCompleted);
            complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) {
                        task.setCompleted(true);
                    } else {
                        task.setCompleted(false);
                    }

                    TaskDTO taskDTO = Mapper.taskToTaskDTOMapper(task);
                    Call<TaskDTO> call = taskService.putTask(taskDTO);
                    call.enqueue(new Callback<TaskDTO>() {

                        @Override
                        public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                            task.setCompleted(response.body().isCompleted());
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<TaskDTO> call, Throwable t) {
                        }
                    });
                }


            });
        }
        TextView number = (TextView) convertView.findViewById(R.id.textViewTaskNumber);
        TextView value = (TextView) convertView.findViewById(R.id.textViewListItem);
        CheckBox complete = (CheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        number.setText(String.valueOf(position + 1));
        value.setText(task.getValue());
        complete.setChecked(task.isCompleted());
        return convertView;
    }
}

