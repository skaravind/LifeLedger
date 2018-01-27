package ketank619.lifeledger;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    static int Request_Camera=2;
    ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==Request_Camera){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scan();
            }
        }

    }

    public void scan( ){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Request_Camera);
        }
        else {
            mScannerView = new ZXingScannerView(this);
            setContentView(mScannerView);
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();

        }

    }

    @Override
    public void handleResult(Result result) {
        final Integer port;
        final String Name;
        String req="";
        ArrayList<String> Options= new ArrayList<>();


        try {
            JSONObject res= new JSONObject(result.getText());

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            port = res.getInt("port");
            Name = res.getString("Name");

            JSONArray Required= res.getJSONArray("Required");

            Options.clear();


            for(int i=0;i<Required.length();i++){
                Options.add(Required.getString(i));

               req=req+Required.getString(i)+"\n";


            }

            builder
                    .setTitle("Data Required")
                    .setMessage(Name+" wants to access your:\n"+req )

                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                           String url="http:///172.31.130.123:"+port+"/transactions/new";
                            SendData(url,"ketan",Name,1000);

                        }
                    })
                    .setNegativeButton("Don't Send", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            scan();

                        }
                    })
                     .show();


        } catch (JSONException e) {
            e.printStackTrace();
        }



        mScannerView.stopCamera();





    }




    void SendData (String url, final String sender,final String recipient,final Integer amount){


        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sender", sender);
        params.put("recipient", recipient);
        params.put("amount", amount);



        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json) {



            Toast.makeText(getApplicationContext(), "Transaction Done....", Toast.LENGTH_SHORT).show();

                        scan();

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.e("Error: ", error.getMessage());


                Toast.makeText(getApplicationContext(), "Error Sending Data....", Toast.LENGTH_SHORT).show();

                scan();
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }

}
