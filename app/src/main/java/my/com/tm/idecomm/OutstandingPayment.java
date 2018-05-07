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

public class OutstandingPayment extends Fragment implements View.OnClickListener {
    RequestQueue rq;
    private ProgressDialog loading;
    private FirebaseAuth firebaseAuth;
    private ListView listView;
    EditText editext;
    Button btnsearch,viewall;
    TextView soa,locks,receiveds,outstandings;

    ImageView refreshs;
    String lock,received,outstanding;



    View myView;
    private String JSON_STRING;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.outstandingpayment, container, false);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = (TextView) myView.findViewById(R.id.time);
        textViewDate.setText(currentDate);

        refreshs = (ImageView)myView.findViewById(R.id.refresh);
        listView = (ListView) myView.findViewById(R.id.list);
        soa = (TextView)myView.findViewById(R.id.time);

        locks = (TextView)myView.findViewById(R.id.lock);
        receiveds = (TextView)myView.findViewById(R.id.received);
        outstandings = (TextView)myView.findViewById(R.id.outstanding);

        viewall = (Button) myView.findViewById(R.id.all);


        refreshs.setOnClickListener(this);
        soa.setOnClickListener(this);
        viewall.setOnClickListener(this);
        locks.setOnClickListener(this);
        receiveds.setOnClickListener(this);
        outstandings.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            // finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        rq = Volley.newRequestQueue(getActivity());
        sendrequest3();

        return myView;

    }
    public void sendrequest3(){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET,Config.URL_OUTSTANDING , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            lock = response.getString("totlockin");
                            received = response.getString("totpayrec");
                            outstanding = response.getString("totoutstanding");

                            locks.setText(lock);
                            receiveds.setText(received);
                            outstandings.setText(outstanding);

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
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(OutstandingPayment.this).attach(OutstandingPayment.this).commit();
            Toast.makeText(getActivity(),"REFRESH",
                    Toast.LENGTH_LONG).show();
           // startActivity(new Intent(getActivity(), OutstandingPayment.class));
        }
        if(view == soa) {
            //finish();
            String currentDateandTime = new SimpleDateFormat("dd-MM-yyyy H:mm:ss").format(new Date());
            Toast.makeText(getActivity(), currentDateandTime, Toast.LENGTH_SHORT).show();
        }
        if(view == viewall) {
             startActivity(new Intent(getActivity(), ListAllOutstading.class));
        }
        if(view == locks) {
            startActivity(new Intent(getActivity(), ListLockin.class));
        }
        if(view == receiveds) {
            startActivity(new Intent(getActivity(), PaymentReceivedLock.class));
        }
        if(view == outstandings) {
            startActivity(new Intent(getActivity(), ListOutstanding.class));
        }
    }
}
