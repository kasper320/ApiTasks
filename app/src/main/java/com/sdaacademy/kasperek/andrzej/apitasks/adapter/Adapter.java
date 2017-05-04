package com.sdaacademy.kasperek.andrzej.apitasks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sdaacademy.kasperek.andrzej.apitasks.MainActivity;
import com.sdaacademy.kasperek.andrzej.apitasks.R;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;
import com.sdaacademy.kasperek.andrzej.apitasks.model.Task;

import java.util.List;

import static com.sdaacademy.kasperek.andrzej.apitasks.R.styleable.View;

/**
 * Created by RENT on 2017-04-29.
 */

public class Adapter extends ArrayAdapter<Task> {
    public Adapter(Context context, int resource, List<Task> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_row, parent, false);

            CheckBox complete = (CheckBox) convertView.findViewById(R.id.checkBoxCompleted);
            complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                private final Task task1 = task;
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) {
                        task1.setCompleated(true);
                    } else {
                        task1.setCompleated(false);
                    }
                    task1.setValue(task.getValue());
                    task1.setId(task.getId());

                }

            });
        }
        TextView number = (TextView) convertView.findViewById(R.id.textViewTaskNumber);
        TextView value = (TextView) convertView.findViewById(R.id.textViewListItem);
        CheckBox complete = (CheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        number.setText(String.valueOf(position + 1));
        value.setText(task.getValue());
        complete.setChecked(task.isCompleated());
        return convertView;
    }
}

