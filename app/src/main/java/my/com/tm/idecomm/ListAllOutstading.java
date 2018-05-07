package my.com.tm.idecomm;

import android.app.ProgressDialog;
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

public class ListAllOutstading extends AppCompatActivity {

    private ProgressDialog loading;

    private ListView listView;
    EditText editext;
    Button btnsearch,back;
    String sitestr;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_outstading);

//        Intent i = getIntent();
        listView = (ListView) findViewById(R.id.list);
//        sitestr = i.getStringExtra("site");
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getJSON();
    }


    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("listout");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.BUYER);
                String b = jo.getString(Config.LOCKIN);
                String c = jo.getString(Config.PAYMENT);
                String d = jo.getString(Config.OUTSTANDING);




                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.BUYER,a);
                employees.put(Config.LOCKIN,b);
                employees.put(Config.PAYMENT,c);
                employees.put(Config.OUTSTANDING,d);




                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listalloutstanding,
                new String[]{Config.BUYER,Config.LOCKIN,Config.PAYMENT,Config.OUTSTANDING},

                new int[]{R.id.satu, R.id.dua, R.id.tiga,R.id.empat});

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
                String s = rh.sendGetRequest(Config.URL_ALLSTANDING);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
