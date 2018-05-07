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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListCompleteSites extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog loading;

    private ListView listView;
    EditText editext;
    Button btnsearch,next,back,home;
    String sitestr;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completedsites);

        Intent i = getIntent();
        listView = (ListView) findViewById(R.id.list);

        next = (Button)findViewById(R.id.buttonall);

        sitestr = i.getStringExtra("site");

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

        getJSON();
    }


    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("Listcom");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_CSITES);
                String b = jo.getString(Config.TAG_CWILAYAH);
                String c = jo.getString(Config.TAG_CEX);
                String d = jo.getString(Config.TAG_CEXTYPE);
                String e = jo.getString(Config.TAG_CAGING);



                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_CSITES,a);
                employees.put(Config.TAG_CWILAYAH,b);
                employees.put(Config.TAG_CEX,c);
                employees.put(Config.TAG_CEXTYPE,d);
                employees.put(Config.TAG_CAGING,e);



                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.activity_total_sites,
                new String[]{Config.TAG_CSITES,Config.TAG_CWILAYAH,Config.TAG_CEX,Config.TAG_CEXTYPE,Config.TAG_CAGING},

                new int[]{R.id.satu, R.id.empat,R.id.dua, R.id.tiga,R.id.lima});

        listView.setAdapter(adapter);

    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getApplicationContext(),"Loading Data","Wait...",false,false);
                // loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //   loading.dismiss();
                //   loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
                //  showData();
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_LIST_COMPLETE+"?state="+sitestr);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onClick(View view) {
//        if (view == next) {
//            //finish();
//            startActivity(new Intent(getApplicationContext(), ListAllComplete.class));
//        }
    }
}
