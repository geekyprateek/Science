package porter.com.porter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements JsonAsyncTask.ShowCourierData,ApiCountAsyncTask.SetApiCount {

    RecyclerView recyclerView;
    RecyclerView.Adapter<CourierViewHolder> recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CourierAdapter courierAdapter;
    ArrayList<CourierData> courierDataList;
    JsonAsyncTask.ShowCourierData showCouriers;
    ApiCountAsyncTask.SetApiCount setApiCount;
    int childPosition=0;
    MyOnclickListener cardClick;
    AppCompatActivity mActivity;
    
    TextView textApiCount;
    TextView textCourierTotal;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        setContentView(R.layout.activity_main);
        courierDataList = new ArrayList<CourierData>();
        mLayoutManager = new GridLayoutManager(this,2);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);

        
        setApiCount=this;
        cardClick=new MyOnclickListener();
        ApiCountAsyncTask apiCount=new ApiCountAsyncTask(this);
        apiCount.setCountInterface(setApiCount);
        apiCount.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        showCouriers=MainActivity.this;
        JsonAsyncTask task=new JsonAsyncTask(this,courierDataList);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        task.setInterface(showCouriers);


        
        textApiCount=(TextView)findViewById(R.id.apiHit);
        textCourierTotal=(TextView)findViewById(R.id.count);
    }



    @Override
    public void setCourierData() {

        Log.d("mainActivity", "setcourier");
        textCourierTotal.setText("Count: " + String.valueOf(courierDataList.size()));
        courierAdapter=new CourierAdapter(this,courierDataList,cardClick);
        recyclerView.setAdapter(courierAdapter);

        courierAdapter.notifyDataSetChanged();
        recyclerView.invalidate();
        recyclerView.refreshDrawableState();

    }


    class MyOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position=recyclerView.getChildAdapterPosition(v);
            Bundle bundle=new Bundle();
            bundle.putParcelable("courierInformation",courierDataList.get(position));
            Intent intent=new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtras(bundle);
            mActivity.startActivity(intent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCount(String count) {
        textApiCount.setText("ApiHit: "+count);
    }
}
