package my.com.tm.idecomm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileVendor extends AppCompatActivity implements ListView.OnItemClickListener{

    private ProgressDialog loading;

    private ListView listView;
    EditText editext;
    Button btnsearch,next,back;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lockin);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        getJSON();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_VENDOR);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_VENDOR);
                String b = jo.getString(Config.TAG_SITEVENDOR);
                String c = jo.getString(Config.z);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_VENDOR,a);
                employees.put(Config.TAG_SITEVENDOR,b);
                employees.put(Config.z,c);


                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.profile_vendor,
                new String[]{Config.TAG_VENDOR,Config.TAG_SITEVENDOR,Config.z},

                new int[]{R.id.satu, R.id.tiga, R.id.dua});

        listView.setAdapter(adapter);

    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_LIST_VENDOR);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getApplicationContext(), DetailVendor.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_VENDOR).toString();
        intent.putExtra(Config.TAG_DETAILVENDOR, empId);

        Context context = getApplicationContext();
        CharSequence text = empId;
        int duration = Toast.LENGTH_SHORT;



        startActivity(intent);
    }

}
