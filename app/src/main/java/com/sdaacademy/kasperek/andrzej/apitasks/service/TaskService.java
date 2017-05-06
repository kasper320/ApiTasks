package com.sdaacademy.kasperek.andrzej.apitasks.service;

import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by RENT on 2017-05-06.
 */

public interface TaskService {
    @GET("/api/task/all/{user}")
    Call<List<TaskDTO>> findAllTasksByUser(@Path("user") Long user);

    @PUT("/api/task")
    Call<TaskDTO> putTask(@Body TaskDTO taskDTO);

    @GET("/api/task/{id}")
    Call<TaskDTO> getTask(@Path("id") Long id);

    @DELETE("/api/task/{id}")
   Call<TaskDTO> deleteTask(@Path("id") Long id);

}
