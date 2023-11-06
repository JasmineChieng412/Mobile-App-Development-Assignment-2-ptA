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

public class PostsActivity extends AppCompatActivity implements Callable {

    TextView textV;
    String url = "https://jsonplaceholder.typicode.com/posts";

    private RecyclerView postRV;
    private RecyclerView.Adapter postAdapter;
    private List<HashMap<String, String>> postsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        postsList = new ArrayList<HashMap<String, String>>();
        postAdapter = new UserAdapter(getApplicationContext(), postsList);

        new backgroundTask2().execute();
    }

    @Override
    public Object call() throws Exception {
        return null;
    }

    public class backgroundTask2 extends AsyncTask<Void,Void,String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PostsActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("Error", builder.toString());
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            StringBuilder stringBuilder = new StringBuilder();


            try {
                JSONArray jArray = new JSONArray(s);
                Intent intent = getIntent();
                String selectedUserId = intent.getStringExtra("selectedUserId");

                for(int i = 0; i<jArray.length(); i++) {
                    JSONObject o = jArray.getJSONObject(i);

                    String userid = o.getString("userId");
                    if (userid.equals(selectedUserId)) {
                        String postId = o.getString("id");
                        String postTitle = o.getString("title");
                        String postBody = o.getString("body");


                        // Users users = new Users(user, id);

                        HashMap<String, String> posts = new HashMap<>();
                        posts.put("userId", userid);
                        posts.put("id", postId);
                        posts.put("title", postTitle);
                        posts.put("body", postBody);
                        postsList.add(posts);

                        //stringBuilder.append("username is ").append(user).append(" city is ").append(city).append("lat is ").append(lat).append("\n");
                        //textV.setText(stringBuilder.toString());
                    }
                }
                setPostRecycler(postsList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setPostRecycler(List<HashMap<String, String>> postsList) {
        postRV = findViewById(R.id.postRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        postRV.setLayoutManager(layoutManager);
        postAdapter = new PostAdapter(this, postsList);
        postRV.setAdapter(postAdapter);
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

    }*/
}