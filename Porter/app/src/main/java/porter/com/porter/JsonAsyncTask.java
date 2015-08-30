package porter.com.porter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by prateek.g2 on 27-Aug-15.
 */
public class JsonAsyncTask extends AsyncTask<Void,Void,Void> {

    ProgressDialog dialog;
    ArrayList<String> listTitle=new ArrayList<String>();
    LinkedHashMap<String,String> childList=new LinkedHashMap<String,String>();
    Context mContext;
    ArrayList<CourierData> courierData;
    private ShowCourierData showCourierData;

    JsonAsyncTask(Context context, ArrayList<CourierData> courierData){
        mContext=context;
        this.courierData=courierData;
    }


    @Override
    protected Void doInBackground(Void... params) {

        fetchJson();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new ProgressDialog(mContext);
        dialog.setMessage("Downloading content");
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        Toast.makeText(mContext,String.valueOf(this.courierData.size()), Toast.LENGTH_SHORT).show();
        showCourierData.setCourierData();
    }

    void fetchJson(){
        try {
            URL url = new URL("http://porter.0x10.info/api/parcel?type=json&query=list_parcel");
            HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // gets the server json data
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String next;
            while ((next = bufferedReader.readLine()) != null){
                JSONObject jsonObject = new JSONObject(next);
                JSONArray ja = (JSONArray) jsonObject.getJSONArray("parcels");

                for (int i = 0; i < ja.length(); i++) {
                    CourierData tempCourier=new CourierData();

                    JSONObject jo = (JSONObject) ja.get(i);//get object from array element
                    tempCourier.setName(jo.getString("name"));

                    String ImageUrl=jo.getString("image");
                    ImageUrl=ImageUrl.replace("\\/","/");
                    tempCourier.setImageUrl(ImageUrl);

                    tempCourier.setDate(jo.getString("date"));
                    tempCourier.setType(jo.getString("type"));
                    tempCourier.setWeight(jo.getString("weight"));
                    tempCourier.setPhone(jo.getString("phone"));
                    tempCourier.setPrice(jo.getString("price"));
                    tempCourier.setQuantity(jo.getString("quantity"));
                    tempCourier.setColor(jo.getString("color"));

                    String link=jo.getString("link");
                    link=link.replace("\\/","/");
                    tempCourier.setLink(link);

                    JSONObject live_location=jo.getJSONObject("live_location");
                    tempCourier.setLongitude(live_location.getString("longitude"));
                    tempCourier.setLatitude(live_location.getString("latitude"));

                    courierData.add(tempCourier);
                    Log.d("porter", tempCourier.printString());
                }
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void setInterface(ShowCourierData data){
        this.showCourierData=data;
    }
    interface ShowCourierData{
        public void setCourierData();
    }
}


