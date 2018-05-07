package my.com.tm.idecomm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PSTN extends Fragment implements View.OnClickListener  {

    RequestQueue rq;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog loading;

    String completed,total,inprogress,remain,moneys,vendors,refresh;

    private Button complete,totalpstn,progress,remaining,vendor,money;
    private TextView soa;

    private ImageView refreshs;
    private ListView listView;
    EditText editext;
    Button btnsearch;
    Button test;

    View myView;
    private String JSON_STRING;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pstn_sites, container, false);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = (TextView) myView.findViewById(R.id.time);
        textViewDate.setText(currentDate);


        listView = (ListView) myView.findViewById(R.id.list);
//        listView.setOnItemClickListener(this);

        soa = (TextView)myView.findViewById(R.id.time);
        refreshs = (ImageView)myView.findViewById(R.id.refresh);

        complete = (Button)myView.findViewById(R.id.completed);
        totalpstn = (Button)myView.findViewById(R.id.totalpstn);
        progress = (Button)myView.findViewById(R.id.inprogress);
        remaining = (Button)myView.findViewById(R.id.remaining);
        vendor = (Button)myView.findViewById(R.id.vendor);
        money = (Button)myView.findViewById(R.id.money);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            // finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        rq = Volley.newRequestQueue(getActivity());
        sendrequest1();

        totalpstn.setOnClickListener(this);
        complete.setOnClickListener(this);
        progress.setOnClickListener(this);
        remaining.setOnClickListener(this);
        soa.setOnClickListener(this);
        vendor.setOnClickListener(this);
        money.setOnClickListener(this);
        refreshs.setOnClickListener(this);


        return myView;

    }

    public void sendrequest1(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.URL_PSTN , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            total = response.getString("totalsite");
                            completed = response.getString("completesite");
                            inprogress = response.getString("inprogresssite");
                            remain = response.getString("pendingsite");
                            moneys = response.getString("payment");
                            vendors = response.getString("payreceive");

                            complete.setText(completed);
                            totalpstn.setText(total);
                            progress.setText(inprogress);
                            remaining.setText(remain);
                            money.setText(moneys);
                            vendor.setText(vendors);


//                    int x=Integer.parseInt(totalpstn.getText().toString());
//                    int y=Integer.parseInt(complete.getText().toString());
//                    int z=x-y;
//                    String zstr = String.valueOf(z);
//                    remaining.setText(zstr);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }

                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        rq.add(jsonObjectRequest);
    }




    @Override
    public void onClick(View view) {
        if(view == refreshs) {
            //finish();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(PSTN.this).attach(PSTN.this).commit();
            Toast.makeText(getActivity(),"REFRESH",
                    Toast.LENGTH_LONG).show();
        }
        if(view == totalpstn) {
            //finish();
            startActivity(new Intent(getActivity(), Total_sites.class));
        }
        if(view == complete) {
            //finish();
            startActivity(new Intent(getActivity(), Completed_Sites.class));
        }
        if(view == progress) {
            //finish();
            startActivity(new Intent(getActivity(), DismantlingProgress.class));
        }
        if(view == remaining) {
            //finish();
            startActivity(new Intent(getActivity(), PendingSites.class));
        }
        if(view == vendor) {
            //finish();
            startActivity(new Intent(getActivity(), ProfileVendor.class));

        }
        if(view == money) {
            //finish();
            startActivity(new Intent(getActivity(), PaymentLock.class));
        }
        if(view == soa) {
            //finish();
            String currentDateandTime = new SimpleDateFormat("dd-MM-yyyy H:mm:ss").format(new Date());
            Toast.makeText(getActivity(), currentDateandTime, Toast.LENGTH_SHORT).show();
        }

    }
}
