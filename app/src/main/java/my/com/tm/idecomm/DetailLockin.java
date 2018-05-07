package my.com.tm.idecomm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailLockin extends AppCompatActivity {

    private ProgressDialog loading;

    private ListView listView;
    EditText editext;
    Button btnsearch,back,home;
    String sitestr;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vendor);

        Intent i = getIntent();
        listView = (ListView) findViewById(R.id.list);
        sitestr = i.getStringExtra("site");

        Toast.makeText(getApplicationContext(), sitestr,
                Toast.LENGTH_LONG).show();

        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(Intent);}
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        getJSON(sitestr);
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("Listpaydetail");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_DETAILVENDOR);
                String b = jo.getString(Config.TAG_DETAILEX);
                String c = jo.getString(Config.TAG_DETAILTYPE);
                String d = jo.getString(Config.TAG_DETAILINV);
                String e = jo.getString(Config.TAG_DETAILSTATUS);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_DETAILVENDOR,a);
                employees.put(Config.TAG_DETAILEX,b);
                employees.put(Config.TAG_DETAILTYPE,c);
                employees.put(Config.TAG_DETAILINV,d);
                employees.put(Config.TAG_DETAILSTATUS,e);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.detailvendor,
                new String[]{Config.TAG_DETAILVENDOR,Config.TAG_DETAILEX,Config.TAG_DETAILTYPE,Config.TAG_DETAILINV,Config.TAG_DETAILSTATUS},

                new int[]{R.id.satu, R.id.dua,R.id.tiga, R.id.empat,R.id.lima});

        listView.setAdapter(adapter);

    }


    private void getJSON(final String str){

        if(!str.isEmpty()){
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
                    String s = null;
                    try {
                        s = rh.sendGetRequest("http://58.27.84.166/mcconline/MCC%20Online%20V3/decom_pstn_lockin.php?buyer="+ URLEncoder.encode(str, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return s;
                }
            }
            GetJSON gj = new GetJSON();
            gj.execute();
        }else{

            Toast.makeText(getApplicationContext(), "Empty request",
                    Toast.LENGTH_LONG).show();

        }
    }
}
