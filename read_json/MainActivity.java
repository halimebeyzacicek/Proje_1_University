package com.example.httpexample3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewid;
    private TextView mTextViewfielt1;
    private TextView mTextViewfielt2;
    private TextView mTextViewfielt3;
    private RequestQueue mQueue;
    private EditText txtname;

    public void init(){
        //textView'e ulaşmaya çalışıyoruz.
        txtname = (EditText) findViewById(R.id.editText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();     //init fonksiyonunu çağırıyoruz.


        mTextViewid=findViewById(R.id.textView1);
        mTextViewfielt1=findViewById(R.id.textView5);
        mTextViewfielt2=findViewById(R.id.textView6);
        mTextViewfielt3=findViewById(R.id.textView7);
        Button buttonParse=findViewById(R.id.btn1);



        mQueue= Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                jsonParse();
                //mTextViewfielt1.append("halime");
            }
        });
    }
    private void jsonParse(){
        String url="https://thingspeak.com/channels/1251782/feed.json";

        String number = txtname.getText().toString();   //EditText okuma/yazma
        final int number_int = Integer.parseInt(number);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=response.getJSONArray("feeds");

                            for(int j=0;j<jsonArray.length();j++){
                                JSONObject feeds=jsonArray.getJSONObject(j);

                                int entry_id=feeds.getInt("entry_id");
                                String field1=feeds.getString("field1");
                                String field2=feeds.getString("field2");
                                String field3=feeds.getString("field3");
                                //int field2=ogrs.getInt("field2");
                                //int field3=ogrs.getInt("field3");

                                if(entry_id == number_int){                  //Eğer bulunduysa aranan satırı i'yi aranan_satira eşitle.
                                    //mTextViewfielt1.append(field1 + String.valueOf(entry_id) + "\n\n");
                                    mTextViewid.append(String.valueOf(entry_id));
                                    mTextViewfielt1.setText(field1);
                                    mTextViewfielt2.setText(field2);
                                    mTextViewfielt3.setText(field3);
                                    //mTextViewfielt3.setText(field1);
                                }

//                                if(entry_id==3){
//                                    mTextViewResult.append(field1 + String.valueOf(entry_id) + "\n\n");
//                                }

                                //id=id*255;
                                //mTextViewResult.append(field1 + String.valueOf(entry_id) + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
