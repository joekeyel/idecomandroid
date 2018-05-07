package my.com.tm.idecomm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListRemaining extends AppCompatActivity {

    private ProgressDialog loading;

    private ListView listView;
    EditText editext;
    Button btnsearch,back,home;
    String sitestr;

    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listremaining);

        Intent i = getIntent();
        listView = (ListView) findViewById(R.id.list);
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
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), PendingSites.class);
                view.getContext().startActivity(Intent);}
        });


        getJSON();
    }

    private void showEmployee(){

        JSONObject jsonObject = null;
        ArrayList<ListRemainingModel> lisremaining = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("Listremain");

            for(int i = 0; i<result.length(); i++){

                //GatedModel gatedModel = new GatedModel();
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_RSITES);
                String b = jo.getString(Config.TAG_ROWNER);
                String c = jo.getString(Config.TAG_RBUYER);
                String d = jo.getString(Config.TAG_RSTAGE);
                String e = jo.getString(Config.TAG_RSTATUS);
                String f = jo.getString(Config.TAG_LOA);
                String g = jo.getString(Config.TAG_F92);
                String h = jo.getString(Config.TAG_INV);
                String ii = jo.getString(Config.TAG_PAY);
                String j = jo.getString(Config.TAG_WP);

                ListRemainingModel gatedModel = new ListRemainingModel();

                gatedModel.setsite(a);
                gatedModel.setexchange(b);
                gatedModel.setsiteown(c);
                gatedModel.setbuyer(d);
                gatedModel.setar(e);
                gatedModel.setloa(f);
                gatedModel.setf92(g);
                gatedModel.setinv(h);
                gatedModel.setpay(ii);
                gatedModel.setwp(j);

                lisremaining.add(gatedModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ListRemainingAdapter sanoAdapter = new ListRemainingAdapter(getApplicationContext(),R.layout.remaining,lisremaining);
        final ListView sanoview = (ListView) findViewById(R.id.list);
        sanoview.setAdapter(sanoAdapter);

        listView.setAdapter(sanoAdapter);

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getApplicationContext(),"Loading Data","Wait...",false,false);
//                loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.gated));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
              //  loading.dismiss();
//                loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.gated));
                JSON_STRING = s;
                showEmployee();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_LIST_REMAINING + "?state="+sitestr);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
