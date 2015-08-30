package porter.com.porter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by prateek on 8/30/2015.
 */
public class CourierViewHolder extends RecyclerView.ViewHolder {


    ImageView parcelImage;
    TextView courierName;
    ImageView courierImage;

    public CourierViewHolder(View itemView) {
        super(itemView);
        this.parcelImage = (ImageView) itemView.findViewById(R.id.parcelImage);
        this.courierName = (TextView) itemView.findViewById(R.id.courierName);
        this.courierImage=(ImageView) itemView.findViewById(R.id.courierImage);

    }

}
