package applab.client.search.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;
import applab.client.search.activity.DashboardActivity;
import applab.client.search.model.Farmer;
import applab.client.search.model.Payload;
import applab.client.search.model.UserDetails;
import applab.client.search.model.Weather;
import applab.client.search.storage.DatabaseHelper;
import applab.client.search.synchronization.IctcCkwIntegrationSync;
import applab.client.search.task.IctcTrackerLogTask;
//import com.google.android.gms.ads.formats.NativeAd;
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
import java.util.Arrays;

/**
 * Created by skwakwa on 9/4/15.
 */
public class ConnectionUtil {

    public static void refreshFarmerInfo(){

    }
    public static void refreshFarmerInfo(final Context context,final Intent intent, final String queryString, final String type,String msg ) {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";
        final  DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Toast.makeText(context, msg,
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    UserDetails u = databaseHelper.getUserItem();
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.ICTC_SERVER_URL + "action="+type+"&a="+u.getSalesForceId()+"&lm="+u.getLastModifiedDate();
                    if(queryString.length()>0)
                        url+="&"+queryString;

                    JSONObject j = new JSONObject();
                    Log.i(this.getClass().getName(),"URL : " + url);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);


                    HttpResponse resp = client.execute(post);
                    Log.i(this.getClass().getName(),"After icctc send");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                    //String server="";
                    Log.i(this.getClass().getName(),"Done");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        Log.i(this.getClass().getName(),line);
                        serverResponse += line;
                    }

                    Log.i(this.getClass().getName(),"Serrver Rsponse  : "+serverResponse);

                    threadMsg(serverResponse,type);
                    Log.i(this.getClass().getName(),"Serrver Rsponse  : 1");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg,String type) {
                Log.i(this.getClass().getName(), "Serrver Rsponse  : 2");
                if (!msg.equals(null) && !msg.equals("")) {
                    Log.i(this.getClass().getName(),"Serrver Rsponse  Dey: 1");
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("message", msg);
                    b.putString("type", type);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }else{
                    Log.i(this.getClass().getName(),"Serrver Rsponse  No Response : -");

                }
            }


            private final Handler handler;

            {
                handler = new Handler() {

                    public void handleMessage(Message msg) {


                        String type  = msg.getData().getString("type");
                        String aResponse = msg.getData().getString("message");

                        if(type.equalsIgnoreCase("login")){
//

                            try {
//                                startActivity(intent);
                                if ((null != aResponse)) {
                                    JSONObject resp = new JSONObject(aResponse);
                                    if(resp.getString("rc").equalsIgnoreCase("00")){
                                        // Intent intent1 = new Intent(,DashboardActivity.class);
                                        Toast.makeText(context,"Login Successful",Toast.LENGTH_LONG).show();
                                        refreshFarmerInfo(context,intent, "us=", "details","Login Successful, Loading Agent Data");
                                        context.startActivity(intent);
                                    }else{
                                        Toast.makeText(context,"Wrong Username or password",Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(context,"Wrong Username or password",Toast.LENGTH_LONG).show();

                                }
                            }catch(Exception e){

                            }

                        }else if(type.equalsIgnoreCase(IctcCkwIntegrationSync.GET_FARMER_DETAILS)){

                            ArrayList<Object> farmerImages = new ArrayList<Object>();

                            ImageDownloader imageDownloader = new ImageDownloader();
                            Log.i(this.getClass().getName(),"Serrver Rsponse  farmer Details  :  fd"+type);
                            if ((null != aResponse)) {

                                ///  TextView test = (TextView) findViewById(R.id.txtcluster);

                                // test.setText(aResponse);
                                Log.i(this.getClass().getName(),"Serrver Rsponse  farmer Details  :  fnot NUll");
                                try {
//                                    JSONObject resp = new JSONArray(aResponse);

                                    JSONArray farmers = new JSONArray(aResponse);

                                    Log.i(this.getClass().getName(),"Serrver Rsponse   FEM : "+farmers.length());

                                    int farmersCnt = 0;
                                    for (int i = 0; i < farmers.length(); i++) {
                                        JSONObject farmer = farmers.getJSONObject(i);
                                        Log.i(this.getClass().getName(),"Serrver Rsponse   Selected : "+farmer.toString());
//                                    Log.i(this.getClass().getName(),"Farmer Name  : " + farmer.getString("lname"));
//                                    cluster1List.add((String) j.get("farmer"));
//
                                        //   String []  keys =  {"fname","lname","nickname","community","village","district","ms","age","gender","ms","noc","nod","edu","cluster","id","maincrop"};
                                        String[] keys
                                                = {

                                                "firstname", "lastname", "nickname", "community", "village",
                                                "district", "region", "age", "gender", "maritalstatus",
                                                "numberofchildren", "numberofdependants", "education", "cluster", "Id",
                                                "sizeplot", "labour", "date_land_ident","loc_land", "target_area",
                                                "exp_price_ton", "variety", "target_nxt_season", "techneed1", "techneed2",
                                                "fbo", "farmarea", "date_plant", "date_man_weed", "pos_contact",
                                                "mon_sell_start", "mon_fin_pro_sold","majorcrop"
                                        };





                                        JSONObject jObj =  farmer.getJSONObject("farmer");
                                        Log.i(this.getClass().getName(),"Server Response   farmer farmer");
//                                      JSONArray bioDatas  = jObj.getJSONArray("biodata");

                                        Log.i(this.getClass().getName(),"Server Response   farmer farmer");

//                                        Log.i(this.getClass().getName(),"Serrver Rsponse   biodata farmer : "+bioDatas.length());
                                        JSONObject bioData  =jObj;// bioDatas.getJSONObject(0);

                                        int  [] bioDataIndex={6,9,4,2,10,32,7,3,8,0,1,11,12,13,14,26};



                                        String[] vals = new String[keys.length];

                                        Arrays.fill(vals,"");
                                        int cnt = 0;
                                        for(int b: bioDataIndex){
                                            try {
                                                Log.i(this.getClass().getName(),"Serrver URL Item : "+keys[b]+" - "+ bioData.getString(keys[b]));
                                                vals[b] = bioData.getString(keys[b]);
                                            } catch (Exception e) {
                                                vals[b] = "";
                                            }

                                        }
                                        long lm= 0l;

                                        try {
                                            lm= bioData.getLong("lm");
                                        }catch(Exception e){
                                            lm=0l;
                                        }


                                        String production="";
                                        String postHarvest="";String budget="";String baselineProduction="";String baselinePostHarvest="";

                                        JSONObject p =jObj.getJSONObject("production");
                                        production = p.toString();


                                        p =jObj.getJSONObject("postharvest");
                                        postHarvest = p.toString();


                                        p =jObj.getJSONObject("baselineproductionbudget");
                                        budget = p.toString();


                                        p =jObj.getJSONObject("baselineproduction");
                                        baselineProduction = p.toString();


                                        p =jObj.getJSONObject("baselinepostharvest");
                                        baselinePostHarvest = p.toString();

                                        p =jObj.getJSONObject("technicalneeds");
                                        String techNeeds = p.toString();

                                        p =jObj.getJSONObject("baselinepostharvestbudget");


                                        System.out.println("P  baselinepostharvestbudget: "+p.toString());
                                        String baselinepostharvestbudget = p.toString();


                                        JSONArray gps= jObj.getJSONArray("farmgps");
                                        if(gps.length()>0){
                                            String fId=bioData.getString("farmerid");;
                                            int gpsLength = gps.length();
                                            databaseHelper.deleteFarmerGPS(fId);

                                            for(int kr=0;kr<gpsLength;kr++){
                                                JSONObject gItem  = gps.getJSONObject(kr);
                                                String x= gItem.getString("x");
                                                String y= gItem.getString("y");
                                                databaseHelper.saveGPSLocation(Double.parseDouble(x),Double.parseDouble(y),fId);
                                            }
                                        }

                                        Log.i(this.getClass().getName(), "Saving techNees "+vals[0]+" / "+vals[1]+techNeeds);


//                                        for (String key : keys) {
//                                            try {
//                                                vals[cnt] = farmer.getString(key);
//                                            } catch (Exception e) {
//                                                vals[cnt] = "";
//                                            }
//                                            cnt++;
//                                        }

                                        Log.i(this.getClass().getName(), "Saving farmer "+i);

                                        String farmerId = bioData.getString("Id");
                                        databaseHelper.deleteFarmer(bioData.getString("Id"));
                                        databaseHelper.updateUser(bioData.getString("Id"),lm);


                                        System.out.println("Production Baseline  : "+bioData.getString("Id")+"---"+baselineProduction);
                                        Farmer f = databaseHelper.saveFarmer(vals[0], vals[1], vals[2], vals[3],
                                                vals[4], vals[5], vals[6], vals[7], vals[8], vals[9], vals[10], vals[11], vals[12], vals[13], vals[14],
                                                vals[15], vals[16], vals[17], vals[18],

                                                vals[19], vals[20], vals[21], vals[22], vals[23], vals[24], vals[25], vals[26], vals[27], vals[28], vals[29], vals[30], vals[31], vals[32]
                                                ,production,postHarvest,budget,baselineProduction,baselinePostHarvest,techNeeds,baselinepostharvestbudget);

                                        farmerImages.add(farmerId + ".jpg");

                                        Log.i(this.getClass().getName(),production);

                                        try{
                                            JSONArray meetings = jObj.getJSONArray("meeting");


                                            if (meetings != null) {

                                                int grpCnt = 1;
                                                int individaulCnt = 1;
                                                for (int k = 0; k < meetings.length(); k++) {
                                                    JSONObject meet = meetings.getJSONObject(k);

                                                    //    public Meeting saveMeeting(String id, St
                                                    // ring type, String title, Date scheduledDate, Date meetingDate, int attended, int meetingIndex, String farmer,
                                                    String typeMeet = meet.getString("ty");
                                                    String title = "";
                                                    // String remark,String crop,String season){

                                                    //{"ty":"individual","midx":"1","sd":"01/03/2105","sea":"1","ed":"30/03/2015"},{"ty":"group","midx":"4","sd":"01/08/2015","sea":"1","ed":"31/08/2015"},
                                                    // {"ty":"group","midx":"1","sd":"01/02/2015","sea":"1","ed":"28/02/2015"}
                                                    if (typeMeet.contains("ind")) {
                                                        title = individaulCnt + " " + typeMeet.toUpperCase() + " Meeting";
                                                        individaulCnt++;
                                                    } else {

                                                        title = grpCnt + " " + typeMeet.toUpperCase() + " Meeting";
                                                        grpCnt++;
                                                    }
                                                    title = AgentVisitUtil.getMeetingTitle(AgentVisitUtil.getMeetingPosition(meet.getInt("midx"),typeMeet));
                                                    databaseHelper.saveMeeting("",

                                                            meet.getString("ty"),
                                                            title,
                                                            IctcCKwUtil.formatSlashDates(meet.getString("sd")),
                                                            null,
                                                            0,// meet.getInt("at"),
                                                            meet.getInt("midx"),
                                                            f.getFarmID(),
                                                            "",
                                                            f.getMainCrop(), meet.getString("sea")
                                                    );


                                                }
                                            }
                                        }catch (Exception e){

                                        }

                                        System.out.println("Farmer After Meeting");
                                    farmersCnt++;
                                    }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                                System.out.println("Downloading Images ");
                                Payload mqp = databaseHelper.getImagePayload(farmerImages);
                                System.out.println("Downloading Images ");
                                IctcTrackerLogTask omUpdateCCHLogTask = new IctcTrackerLogTask(context,"pp");
                                System.out.println("Payload stask ");
                                omUpdateCCHLogTask.execute(mqp);
                                System.out.println("Payload execute ");

                                // ALERT MESSAGE
                                Toast.makeText(
                                        context,
                                        "Data Recieved: ",

                                        Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
                                if(null != intent)
                                    context.startActivity(intent);

                            } else {

                                //ALERT MESSAGE
                                Toast.makeText(
                                        context,
                                        "Not Got Response From Server.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    }
                };
            }

        });

        background.start();
        // return  serverResponse;
    }

    public static void refreshWeather(final Context context, final String type,String msg) {

        refreshWeather(context, type, msg, null);
    }
    public static void refreshWeather(final Context context, final String type,String msg, final FragmentStatePagerAdapter pager) {

        // String url="http://sandbox-ictchallenge.cs80.force.com/getTest";
        final  DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Toast.makeText(context, msg,
                Toast.LENGTH_SHORT).show();


        Thread background = new Thread(new Runnable() {
            @Override
            public void run() {

                try {      /* TODO output your page here. You may use following sample code. */
                    String serverResponse = "";
                    String url = IctcCkwIntegrationSync.WEATHER_URL;


                    JSONObject j = new JSONObject();
                    Log.i(this.getClass().getName(),"URL : " + url);

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);


                    HttpResponse resp = client.execute(post);
                    Log.i(this.getClass().getName(),"After icctc send");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                    //String server="";
                    Log.i(this.getClass().getName(),"Done");
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        Log.i(this.getClass().getName(),line);
                        serverResponse += line;
                    }

                    Log.i(this.getClass().getName(),"Serrver Weather Rsponse  : "+serverResponse);

                    threadMsg(serverResponse,type);
                    Log.i(this.getClass().getName(),"Serrver Rsponse  : 1");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg,String type) {
                Log.i(this.getClass().getName(), "Serrver Weather Rsponse  : 2");
                if (!msg.equals(null) && !msg.equals("")) {
                    Log.i(this.getClass().getName(),"Serrver Rsponse  Dey: 1");
                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();

                    b.putString("message", msg);

                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }else{
                    Log.i(this.getClass().getName(),"Serrver Weather Rsponse  No Response : -");

                }
            }


            private final Handler handler;

            {
                handler = new Handler() {

                    public void handleMessage(Message msg) {



                        String aResponse = msg.getData().getString("message");


                            Log.i(this.getClass().getName(),"Serrver Rsponse  weather Details  :  Weather");
                            if ((null != aResponse)) {

                                ///  TextView test = (TextView) findViewById(R.id.txtcluster);

                                // test.setText(aResponse);
                                Log.i(this.getClass().getName(),"Serrver Rsponse  weather Details  :  fnot NUll");
                                try {
//                                    JSONObject resp = new JSONArray(aResponse);

                                    JSONArray weathers = new JSONArray(aResponse);

                                    Log.i(this.getClass().getName(),"Serrver Rsponse   FEM : "+weathers.length());
                                    databaseHelper.resetWeather();
                                    int farmersCnt = 0;
                                    for (int i = 0; i < weathers.length(); i++) {
                                        JSONObject weather = weathers.getJSONObject(i);
                                        Weather w = new Weather();
                                        w.setTime(weather.getLong("time"));
                                        w.setLocation(weather.getString("city"));
                                        w.setDetail(weather.getString("detail"));
                                        w.setTemprature(weather.getDouble("temp"));
                                        w.setMaxTemprature(weather.getDouble("max_temp"));
                                        w.setMinTemprature(weather.getDouble("min_temp"));
                                        Log.i(this.getClass().getName(),"Serrver Rsponse   Selected : "+weather.toString());
//

                                        long wId =   databaseHelper.saveWeather(w);

                                        System.out.println("Weather ID");

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // ALERT MESSAGE
                                Toast.makeText(
                                        context,
                                        "Data Recieved  For weather ",

                                        Toast.LENGTH_SHORT).show();

                                try{

                                    if(null!=pager) {

                                        System.out.println("Notifying Pager");
                                        pager.notifyDataSetChanged();
                                    }
                                }catch(Exception e){

                                }
//
                            }

                        }



                };
            }

        });

        background.start();
        // return  serverResponse;
    }



    private boolean isInternetConnectionActive(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }
}
