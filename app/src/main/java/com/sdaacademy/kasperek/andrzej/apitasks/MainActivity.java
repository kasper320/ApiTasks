package com.sdaacademy.kasperek.andrzej.apitasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.sdaacademy.kasperek.andrzej.apitasks.adapter.Adapter;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.TaskDTO;
import com.sdaacademy.kasperek.andrzej.apitasks.dto.mapper.Mapper;
import com.sdaacademy.kasperek.andrzej.apitasks.model.Task;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private TaskDTO taskDTO;
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
        adapter = new Adapter(this, R.layout.single_row, taskList);
        show.setAdapter(adapter);
        new MyTask().execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
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

    public class AddNewTask extends AsyncTask<Void, Void, TaskDTO> {

        @Override
        protected TaskDTO doInBackground(Void... params) {

            RestTemplate restTemplate = new RestTemplate();
            Gson gson = new Gson();
            String url = "https://shrouded-fjord-81597.herokuapp.com/api/task";
            String requestBody = gson.toJson(taskDTO);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entity = new HttpEntity<Object>(requestBody, headers);
            ResponseEntity<TaskDTO> response = restTemplate.exchange(url, HttpMethod.PUT, entity, TaskDTO.class);
            TaskDTO responseBody = response.getBody();

            return responseBody;
        }

    }

    @OnClick
    public void onClick(View view) {
        taskDTO = new TaskDTO();
        taskDTO.setValue(dataEntryView.getText().toString());
        taskDTO.setUser(2);
        taskDTO.setCompleated(false);
        new AddNewTask().execute();
        taskList.clear();
        new MyTask().execute();
    }


}
