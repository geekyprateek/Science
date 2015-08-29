package science.inc.com.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
    ArrayList<NewsData> newsData;
    private showNewsData ShowNewsData;

    JsonAsyncTask(Context context, ArrayList<NewsData> newsData){
        mContext=context;
        this.newsData=newsData;
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
        Toast.makeText(mContext,String.valueOf(this.newsData.size()), Toast.LENGTH_SHORT).show();
        ShowNewsData.setNews();
    }

    void fetchJson(){
        try {
            URL url = new URL("http://fierce-mesa-1366.herokuapp.com");
            HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // gets the server json data
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String next;
            while ((next = bufferedReader.readLine()) != null){
                JSONArray ja = new JSONArray(next);
                for (int i = 0; i < ja.length(); i++) {
                    NewsData tempNews=new NewsData();

                    JSONObject jo = (JSONObject) ja.get(i);//get object from array element

                    tempNews.setCategoryName(jo.getString("CategoryName"));
                    tempNews.setCategoryId(Integer.parseInt(jo.getString("CategoryId")));

                    String ImageUrl=jo.getString("ImageUrl");
                    ImageUrl=ImageUrl.replace("\\/","/");
                    tempNews.setImageUrl(ImageUrl);

                    JSONArray array=jo.getJSONArray("Subcategories");

                    LinkedHashMap<String,String> children=new LinkedHashMap<String,String>();
                    LinkedList<String> subDescription=new LinkedList<String>();
                    for(int l=0;l<array.length();l++){
                        JSONObject subcat = (JSONObject) array.get(l);
                        String subCat=subcat.getString("subCategoryName");
                        String description=subcat.getString("description");
                        String subImageUrl= subcat.getString("ImageUrl");
                        children.put(subCat,subImageUrl);
                        subDescription.add(description);
                    }
                    tempNews.setSubChildDescription(subDescription);
                    tempNews.setSubcategories(children);

                    this.newsData.add(tempNews);
                    //childList.put(listTitle.get(i),children);
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

    public void setInterface(showNewsData data){
        this.ShowNewsData=data;
    }
    interface showNewsData{
        public void setNews();
    }
}


