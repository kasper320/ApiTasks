package com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper;

import com.sdaacademy.kasperek.andrzej.apitasks.R;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;
import com.sdaacademy.kasperek.andrzej.apitasks.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-04-29.
 */

public class Mapper {
    public static List<Task> taskMapper(TaskDTO[] taskDTO) {

        List<Task> taskList = new ArrayList<>();
        for (TaskDTO dto : taskDTO) {
            Task task = new Task();
            task.setId(dto.getId());
            task.setCompleted(dto.isCompleted());
            task.setValue(dto.getValue());
            task.setId(dto.getId());
            taskList.add(task);
        }
        return taskList;
    }

    public static List<Task> taskMapperRatrofit(List<TaskDTO> taskDTO) {

        List<Task> taskList = new ArrayList<>();
        for (TaskDTO dto : taskDTO) {
            Task task = new Task();
            task.setId(dto.getId());
            task.setCompleted(dto.isCompleted());
            task.setValue(dto.getValue());
            task.setId(dto.getId());
            taskList.add(task);
        }
        return taskList;
    }

    public static TaskDTO taskToTaskDTOMapper(Task task) {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setCompleted(task.isCompleted());
        taskDTO.setValue(task.getValue());
        taskDTO.setUser(102);
        return taskDTO;
    }

}