package curtin.edu.parta;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SelectedUserDetails extends AppCompatActivity implements Callable {

    Button postB;
    TextView selText, userText, emailText;
    private List<HashMap<String, String>> selectedList;
    String url = "https://jsonplaceholder.typicode.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user_details);

        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        selectedList = new ArrayList<HashMap<String, String>>();
        emailText = (TextView) findViewById(R.id.selectedEmailTextView);
        new backgroundTask3().execute();

        Intent intent = getIntent();
       // String selectedUser = intent.getStringExtra("selectedUser");
        String selectedMainId = intent.getStringExtra("selectedId");//use this user id to compare user id and get all info of userId only

        selText = (TextView) findViewById(R.id.selectedTextView);
        //userText = (TextView) findViewById(R.id.selectedUsernameTextView);
       // emailText = (TextView) findViewById(R.id.selectedEmailTextView);

        selText.setText(selectedMainId);
        //userText.setText(selectedUser);



        postB = (Button) findViewById(R.id.postButton);
        postB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(SelectedUserDetails.this, "Loading Posts", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SelectedUserDetails.this, PostsActivity.class);
                i.putExtra("selectedUserId", selectedMainId);
                startActivity(i);

            }
        });
    }

    @Override
    public Object call() throws Exception {
        return null;
    }

    public class backgroundTask3 extends AsyncTask<Void,Void,String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SelectedUserDetails.this);
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
                String idNum = intent.getStringExtra("selectedId");

                for(int i = 0; i<jArray.length(); i++) {
                    JSONObject o = jArray.getJSONObject(i);
                    String selectedId = o.getString("id");
                    if (selectedId.equals(idNum)) {
                        String name = o.getString("name");
                        String user = o.getString("username");
                        String email = o.getString("email");
                        String address = o.getString("address");

                        JSONObject o2 = new JSONObject(address);
                        String street = o2.getString("street");
                        String suite = o2.getString("suite");
                        String city = o2.getString("city");
                        String zipcode = o2.getString("zipcode");
                        String geo = o2.getString("geo");

                        JSONObject o3 = new JSONObject(geo);
                        String lat = o3.getString("lat");
                        String lng = o3.getString("lng");

                        //JSONObject o4 = new JSONObject(i);
                        String phone = o.getString("phone");
                        String website = o.getString("website");
                        String company = o.getString("company");

                        JSONObject o4 = new JSONObject(company);
                        String cName = o4.getString("name");
                        String catchPhrase = o4.getString("catchPhrase");
                        String bs = o4.getString("bs");

                        HashMap<String, String> selected = new HashMap<>();
                        selected.put("id", selectedId);
                        selected.put("name", name);
                        selected.put("username", user);
                        selected.put("email", email);
                        selected.put("street", street);
                        selected.put("suite", suite);
                        selected.put("city", city);
                        selected.put("zipcode", zipcode);
                        selected.put("lat", lat);
                        selected.put("lng", lng);
                        selected.put("phone", phone);
                        selected.put("website", website);
                        selected.put("name", cName);
                        selected.put("catchPhrase", catchPhrase);
                        selected.put("bs", bs);
                        selectedList.add(selected);

                        stringBuilder.append("Name: "+name+"\n"
                                +"Username: "+user+"\n"
                                +"Email: "+email+"\n"
                                +"Phone: "+phone+"\n"
                                +"Website: "+website+"\n\n"
                                +"Company:\n"
                                +"Company Name: "+cName+"\n"
                                +"Catchphrase: "+catchPhrase+"\n"
                                +"Business: "+bs+"\n\n"
                                +"Address:\n"
                                +"Street: "+street+"\n"
                                +"Suite: "+suite+"\n"
                                +"City: "+city+"\n"
                                +"Zipcode: "+zipcode+"\n\n"
                                +"Geographical Location:\n"
                                +"Latitude: "+lat+"\n"
                                +"Longitude: "+lng+"\n");
                        emailText.setText(stringBuilder.toString());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}