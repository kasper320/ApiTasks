package com.sdaacademy.kasperek.andrzej.apitasks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sdaacademy.kasperek.andrzej.apitasks.adapter.Adapter;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper.Mapper;
import com.sdaacademy.kasperek.andrzej.apitasks.model.Task;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.addPrintersActivity;
import static android.R.attr.onClick;
import static com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper.Mapper.taskMapper;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.show)
    ListView show;
    @BindView(R.id.dataEntry)
    EditText dataEntryView;
    private List<TaskDTO> taskDTOList;
    private List<Task> taskList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        taskList = new ArrayList<>();
        adapter = new Adapter(this,R.layout.single_row,taskList);
        show.setAdapter(adapter);
        new MyTask().execute();
    }
    @OnClick
    public void onClick(View view){
        new MyTask().execute();
    }


    private class MyTask extends AsyncTask<Void, Void, TaskDTO[]> {
        private String url = "https://shrouded-fjord-81597.herokuapp.com/api/task/all/2";
        private String readStream;
        private TaskDTO task;
        private TaskDTO[] taskTable;
        private StringBuilder stringBuilder;

        @Override
        protected TaskDTO[] doInBackground(Void... params) {
            RestTemplate taskTemplate = new RestTemplate();
            taskTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            taskTable = taskTemplate.getForObject(url, TaskDTO[].class);
            return taskTable;
        }

        @Override
        protected void onPostExecute(TaskDTO[] taskTable) {
            taskList.addAll(taskMapper(taskTable));
            adapter.notifyDataSetChanged();
        }
    }
}
