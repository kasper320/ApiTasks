package com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper;

import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;
import com.sdaacademy.kasperek.andrzej.apitasks.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-04-29.
 */

public class Mapper {
    public static List<Task> taskMapper(TaskDTO[] taskDTO){

        List<Task> taskList = new ArrayList<>();
        for(TaskDTO dto: taskDTO){
            Task task = new Task();
            task.setId(dto.getId());
            task.setCompleated(dto.isCompleated());
            task.setValue(dto.getValue());
            task.setId(2);
            taskList.add(task);
        }
        return taskList;
    }
}
