package applab.client.search.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import applab.client.search.MainActivity;
import applab.client.search.R;
import applab.client.search.adapters.DashboardMenuAdapter;
import applab.client.search.model.Farmer;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.storage.DatabaseHelperConstants;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Software Developer on 30/07/2015.
 */
public class DashboardActivity extends Activity {
    private GridView grid_menu;
    private TableRow tableRow_communities;
    private TableRow tableRow_farmers;
    private TableRow tableRow_taroWorks;
    private TableRow tableRow_ckw;

    DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        grid_menu = (GridView) findViewById(R.id.gridView);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        helper = new DatabaseHelper(getBaseContext());

        final View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.textView_title);
        mTitleTextView.setText("Dashboard");


        TextView st = (TextView) findViewById(R.id.farmer_cnt);
        if (null == st) {
            System.out.println("farmer cnt shd not be null ");
        } else
            st.setText(helper.farmerCount() + " Farmers");

        TextView tv = (TextView) findViewById(R.id.community_count);
        tv.setText(helper.farmerCountByCommunityGroup().size() + " Communities");


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        Button mButton = (Button) mCustomView.findViewById(R.id.search_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FarmerActivity.class);
                intent.putExtra("type", "search");
                EditText tv = (EditText) mCustomView.findViewById(R.id.bar_search_text);
                String q = tv.getText().toString();
                System.out.println("SearchItem : " + q);
                System.out.println("Type : Search");
                intent.putExtra("q", q);
                startActivity(intent);
            }
        });
//getData();
        String[] titles = {"Prices", "ID Generator", "Clusters", "Communities"};
        tableRow_communities = (TableRow) findViewById(R.id.tableRow_communities);
        tableRow_farmers = (TableRow) findViewById(R.id.tableRow_farmers);
        tableRow_taroWorks = (TableRow) findViewById(R.id.tableRow_taroWorks);
        tableRow_ckw = (TableRow) findViewById(R.id.tableRow_ckw);
        int[] images = {R.drawable.ic_cedi, R.drawable.ic_id, R.drawable.ic_clusters, R.drawable.ic_community};
        DashboardMenuAdapter adapter = new DashboardMenuAdapter(DashboardActivity.this, images, titles);
        grid_menu.setAdapter(adapter);
        tableRow_communities.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CommunityActivity.class);
                startActivity(intent);
            }
        });
        tableRow_farmers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FarmerActivity.class);
                intent.putExtra("type", "farmer");
                startActivity(intent);
            }
        });
        tableRow_ckw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tableRow_taroWorks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("org.grameen.taro");
                startActivity(launchIntent);
            }
        });
        grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(DashboardActivity.this, PricesActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(DashboardActivity.this, ClusterActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(DashboardActivity.this, CommunityActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }


    public void getData() {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";

        Toast.makeText(getBaseContext(),
                "Please wait, connecting to server Dashboard.",
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                try {      /* TODO output your page here. You may use following sample code. */
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.ICTC_SERVER_URL;

                    JSONObject j = new JSONObject();
                    System.out.println("URL : " + url);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);


                    HttpResponse resp = client.execute(post);
                    System.out.println("After icctc send");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                    //String server="";
                    System.out.println("Sent Response");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                        serverResponse += line;
                    }

//                    System.out.println("Serrver Rsponse  : "+serverResponse);

                    threadMsg(serverResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg) {

                if (!msg.equals(null) && !msg.equals("")) {
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("message", msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }


            private final Handler handler;

            {
                handler = new Handler() {

                    public void handleMessage(Message msg) {


                        String aResponse = msg.getData().getString("message");

                        if ((null != aResponse)) {

                            ///  TextView test = (TextView) findViewById(R.id.txtcluster);

                            // test.setText(aResponse);

                            try {
                                JSONObject resp = new JSONObject(aResponse);

                                JSONArray farmers = (JSONArray) resp.get("farmer");
                                List<Farmer> myFarmers = new ArrayList<Farmer>();

                                int farmersCnt = 0;
                                for (int i = 0; i < farmers.length(); i++) {
                                    JSONObject farmer = farmers.getJSONObject(i);
                                    System.out.println("Farmer Name  : " + farmer.getString("lname"));
//                                    myFarmers.add(new Farmer(farmer.getString("fname"),farmer.getString("lName"),farmer.getString("nickname"),farmer.getString("community"),farmer.getString("village"),farmer.getString("district"),farmer.getString("region"),farmer.getString("age"),farmer.getString("gender"),farmer.getString("ms"),farmer.getString("noc"),farmer.getString("nod"),farmer.getString("edu"),farmer.getString("cluster"),farmer.getString("id")));

//
//     cluster1List.add((String) j.get("farmer"));
                                    farmersCnt++;
                                }


                                //sending data into cluster 1
                                /**  Intent cluster1Intent = new Intent(MainActivity.this, Cluster1.class);
                                 Bundle b = new Bundle();
                                 b.putStringArrayList("cluster1", (ArrayList<String>) cluster1List);
                                 cluster1Intent.putExtra("clusterbundle", b);
                                 startActivity(cluster1Intent);

                                 //sending data into cluster 2
                                 Intent cluster2Intent = new Intent(MainActivity.this, Cluster2.class);
                                 Bundle b2 = new Bundle();
                                 b2.putStringArrayList("cluster2", (ArrayList<String>) cluster1List);
                                 cluster2Intent.putExtra("clusterbundle",b2);
                                 startActivity(cluster2Intent);


                                 //sending data into cluster 3
                                 Intent cluster3Intent = new Intent(MainActivity.this, Cluster3.class);
                                 Bundle b3 = new Bundle();
                                 b3.putStringArrayList("cluster2", (ArrayList<String>) cluster1List);
                                 cluster3Intent.putExtra("clusterbundle",b3);
                                 startActivity(cluster3Intent);
                                 **/


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            // ALERT MESSAGE
                            Toast.makeText(
                                    getBaseContext(),
                                    "Cluster Data Recieved: ",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            //ALERT MESSAGE
                            Toast.makeText(
                                    getBaseContext(),
                                    "Not Got Response From Server.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                };
            }

        });

        background.start();
        // return  serverResponse;
    }


}