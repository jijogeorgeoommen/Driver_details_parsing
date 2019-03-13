package com.example.driver_details_parsing;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    RequestParams params;

    ArrayList<String>driver;
    ArrayList<String>namelist;
    ArrayList<String>numberlist;
    ArrayList<String>lic_no;

    ListView listview;
    LayoutInflater inflate;

    String url="http://srishti-systems.info/projects/smartambulance/viewdriver.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview=findViewById(R.id.lview);

        client=new AsyncHttpClient();
        params=new RequestParams();

        driver=new ArrayList<>();
        namelist=new ArrayList<>();
        numberlist=new ArrayList<>();
        lic_no=new ArrayList<>();

        Log.e("In","Out");

        client.get(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try{
                    Log.e("inn","outt");
                    JSONObject jobjmain=new JSONObject(content);
                    if (jobjmain.getString("status").equals("success")){
                        JSONArray jarray=jobjmain.getJSONArray("Driver_details");
                        for(int i=0;i<jarray.length();i++){
                            JSONObject jobj=jarray.getJSONObject(i);
                            String nam=jobj.getString("name");
                            namelist.add("Name :"+nam);
                            String num=jobj.getString("phone");
                            numberlist.add("Number :" +num);
                            String lic=jobj.getString("license");
                            lic_no.add("License No. :"+lic);



                        }

                    }
                    adapter adp=new adapter();
                    listview.setAdapter(adp);


                }catch (Exception e ){

                }
            }
        });
    }

    class adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return namelist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflate=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflate.inflate(R.layout.listviewxml,null);
            TextView name=convertView.findViewById(R.id.namexml);
            name.setText(namelist.get(position));
            TextView number=convertView.findViewById(R.id.numberxml);
            number.setText(numberlist.get(position));
            TextView licno=convertView.findViewById(R.id.lcnoxml);
            licno.setText(lic_no.get(position));

            return convertView;
        }
    }
}
