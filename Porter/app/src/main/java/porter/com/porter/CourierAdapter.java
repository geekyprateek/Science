package porter.com.porter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by prateek on 8/30/2015.
 */
public class CourierAdapter extends RecyclerView.Adapter<CourierViewHolder> {

    ArrayList<CourierData> courierData;
    Context mContext;
    View.OnClickListener listener;

    CourierAdapter(Context context,ArrayList<CourierData> data,View.OnClickListener listener){
        this.courierData=data;
        this.mContext=context;
        this.listener=listener;
    }

    @Override
    public CourierViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.courier_card_view,null);
        CourierViewHolder holder=new CourierViewHolder(v);
        v.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(CourierViewHolder courierViewHolder, int i) {
        Picasso.with(mContext).load(courierData.get(i).getImageUrl()).
                placeholder( R.drawable.progress_animation).error(R.drawable.error)
                    .resize(50,50).into(courierViewHolder.courierImage);

        courierViewHolder.courierName.setText(courierData.get(i).getName());
        courierViewHolder.parcelImage.setImageResource(R.drawable.box_icon);



        Log.d("porter", courierData.get(i).printString());
        Log.d("adapter","onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return courierData.size();
    }
}
