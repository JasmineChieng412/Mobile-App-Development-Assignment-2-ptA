package curtin.edu.parta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements Callable {

    String url = "https://jsonplaceholder.typicode.com/users";

    private RecyclerView userRV;
    private RecyclerView.Adapter userAdapter;
    private List<HashMap<String, String>> usersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        usersList = new ArrayList<HashMap<String, String>>();
        userAdapter = new UserAdapter(getApplicationContext(), usersList);
        new backgroundTask().execute();

        /*service.execute(new Runnable()  {
            @Override
            public void run() {

                StringBuilder builder = null;
                try {
                    URL urlbackground = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlbackground.openConnection();
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    BufferedReader buffer = new BufferedReader(reader);
                    String line = "";
                    builder = new StringBuilder();
                    while ((line = buffer.readLine()) != null){

                        builder.append(line);
                    }

                    JSONArray jArray = new JSONArray(builder);
                    for(int i = 0; i<jArray.length(); i++){
                        JSONObject o =  jArray.getJSONObject(i);
                        String id = o.getString("id");
                        String user = o.getString("username");

                        HashMap<String, String> users2 = new HashMap<>();
                        users2.put("id", id);
                        users2.put("username", user);
                        usersList.add(users2);
                    }
                      //setUserRecycler(usersList);
                    userRV = findViewById(R.id.userRecyclerView);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                    userRV.setLayoutManager(layoutManager);
                    userAdapter = new UserAdapter(MainActivity.this, usersList);
                    userRV.setAdapter(userAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(usersList!=null){
                       // setUserRecycler(usersList);
                            userRV = findViewById(R.id.userRecyclerView);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                            userRV.setLayoutManager(layoutManager);
                            userAdapter = new UserAdapter(MainActivity.this, usersList);
                            userRV.setAdapter(userAdapter);
                    }
                }
            });

        }
        });
        Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();*/

    }

    @Override
    public Object call() throws Exception {
        return null;
    }

    public class backgroundTask extends AsyncTask<Void,Void,String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = null;
            try {
                URL urlbackground = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlbackground.openConnection();
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                BufferedReader buffer = new BufferedReader(reader);
                String line = "";
                stringBuilder = new StringBuilder();
                while ((line = buffer.readLine()) != null){
                    stringBuilder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            StringBuilder stringBuilder = new StringBuilder();

            try {
                JSONArray jArray = new JSONArray(s);
                for(int i = 0; i<jArray.length(); i++){
                    JSONObject o =  jArray.getJSONObject(i);
                    String id = o.getString("id");
                    String user = o.getString("username");

                    HashMap<String, String> users2 = new HashMap<>();
                    users2.put("id", id);
                    users2.put("username", user);
                    usersList.add(users2);
                }
                setUserRecycler(usersList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setUserRecycler(List<HashMap<String, String>> usersList) {
        userRV = findViewById(R.id.userRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        userRV.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(this, usersList);
        userRV.setAdapter(userAdapter);
    }
}