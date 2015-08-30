package porter.com.porter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by prateek on 8/30/2015.
 */
public class ApiCountAsyncTask extends AsyncTask<Void,Void,Void> {

    SetApiCount SetCount;
    String apiHits;
    ProgressDialog dialog;
    Context mContext;

    ApiCountAsyncTask(Context context){
    mContext=context;
    }

    interface SetApiCount{
        void setCount(String count);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        SetCount.setCount(apiHits);
    }

    void setCountInterface(SetApiCount count){
        this.SetCount=count;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new ProgressDialog(mContext);
        dialog.setMessage("Downloading content");
        dialog.setCancelable(false);
        //dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            String hits=null;
            URL url = new URL("https://porter.0x10.info/api/parcel?type=json&query=api_hits");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // gets the server json data
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String next;
            while ((next = bufferedReader.readLine()) != null) {
                JSONObject jo = new JSONObject(next);
                 apiHits=jo.getString("api_hits");
                 Log.d("APiHits", apiHits);

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
        return null;
    }


}
