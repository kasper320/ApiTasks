package com.sdaacademy.kasperek.andrzej.apitasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sdaacademy.kasperek.andrzej.apitasks.adapter.Adapter;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper.Mapper;
import com.sdaacademy.kasperek.andrzej.apitasks.model.Task;
import com.sdaacademy.kasperek.andrzej.apitasks.service.TaskService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;
import static android.app.PendingIntent.getActivity;
import static com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper.Mapper.taskMapperRatrofit;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.updateButton)
    Button updateButton;
    @BindView(R.id.show)
    ListView show;
    @BindView(R.id.dataEntry)
    EditText dataEntryView;
    private List<TaskDTO> taskDTOList;
    private List<Task> taskList;
    Adapter adapter;
    private TaskDTO taskDTO;
    private Long positionId;
    private TaskService taskService;
    private final String BASE_URL = "https://shrouded-fjord-81597.herokuapp.com";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        taskList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taskService = retrofit.create(TaskService.class);
        adapter = new Adapter(this, R.layout.single_row, taskList, taskService);
        show.setAdapter(adapter);
        getAllTasks();
        deleteSelectedItem();


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < taskList.size(); i++) {
                    TaskDTO taskDTO = new TaskDTO();
                    Task task = taskList.get(i);
                    View view = show.getChildAt(i);
                    EditText editText = (EditText) view.findViewById(R.id.textViewListItem);
                    String value = editText.getText().toString();
                    taskDTO.setId(task.getId());
                    taskDTO.setCompleted(task.isCompleted());
                    taskDTO.setValue(value);
                    taskDTO.setUser(102);
                    putTask(taskDTO);

                }
                getAllTasks();
            }

        });
    }

    private void deleteSelectedItem() {
        show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                positionId = taskList.get(position).getId();
                Call<TaskDTO> call = taskService.deleteTask(positionId);
                call.enqueue(new Callback<TaskDTO>() {
                    @Override
                    public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                        refreshListView();
                        Toast.makeText(getApplicationContext(), "Task has been deleted!",
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<TaskDTO> call, Throwable t) {

                    }
                });
                return false;
            }
        });
    }

    private void getAllTasks() {
        Call<List<TaskDTO>> call = taskService.findAllTasksByUser(102l);
        call.enqueue(new Callback<List<TaskDTO>>() {
            @Override
            public void onResponse(Call<List<TaskDTO>> call, Response<List<TaskDTO>> response) {
                taskDTOList = response.body();
                taskList.clear();
                taskList.addAll(taskMapperRatrofit(taskDTOList));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TaskDTO>> call, Throwable t) {

            }
        });
    }

    @OnClick
    public void onClick(View view) {
        taskDTO = new TaskDTO();
        taskDTO.setValue(dataEntryView.getText().toString());
        taskDTO.setUser(102);
        taskDTO.setCompleted(false);

        putTask(taskDTO);
    }

    private void putTask(TaskDTO taskDTO) {
        Call<TaskDTO> call = taskService.putTask(taskDTO);
        call.enqueue(new Callback<TaskDTO>() {

            @Override
            public void onResponse(Call<TaskDTO> call, Response<TaskDTO> response) {
                refreshListView();
            }

            @Override
            public void onFailure(Call<TaskDTO> call, Throwable t) {

            }
        });
    }

    public void refreshListView() {
        taskList.clear();
        getAllTasks();
    }

}