package porter.com.porter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.squareup.picasso.Picasso;

/**
 * Created by prateek on 8/30/2015.
 */
public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    CourierData data;
    Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_card);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        data = (CourierData)getIntent().getParcelableExtra("courierInformation");
        mActivity=this;
        TextView  textType=(TextView)findViewById(R.id.textType);
        TextView  textPrice=(TextView)findViewById(R.id.textPrice);
        TextView  textQuantity=(TextView)findViewById(R.id.textQuantity);
        TextView  textWeight=(TextView)findViewById(R.id.textWeight);
        TextView  textNumber=(TextView)findViewById(R.id.textPhone);
        TextView  textColor=(TextView)findViewById(R.id.textColor);
        ImageView productImage=(ImageView)findViewById(R.id.productImage);
        Picasso.with(this).load(data.getImageUrl()).
                placeholder( R.drawable.progress_animation).error(R.drawable.error)
                .resize(150,150).into(productImage);
        textType.setText(data.getName());
        textPrice.setText(data.getPrice());
        textQuantity.setText(data.getQuantity());
        textWeight.setText(data.getWeight());
        textNumber.setText(data.getPhone());
        textColor.setBackgroundColor(Color.parseColor(data.getColor()));
        TextView date=(TextView)findViewById(R.id.productDate);

        String d="ETA :"+data.getDate().substring(0,2)+"/"+data.getDate().substring(2,4)+"/"+data.getDate().substring(4,data.getDate().length()-1);
        date.setText(d);


        Button shareLink=(Button)findViewById(R.id.ShareLink);
        shareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLink();
            }
        });
        Button close=(Button)findViewById(R.id.ClosePage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Porter","finish");
               mActivity.finish();
            }
        });
        Button shareDetail=(Button)findViewById(R.id.ShareDetail);
        shareDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDetail();
            }
        });
        Button SMS=(Button)findViewById(R.id.SendSMS);
        SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSMS();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Double latitude=Double.valueOf(data.getLatitude());
        Double longitude=Double.valueOf(data.getLongitude());

        LatLng CurrentLocation = new LatLng(latitude,longitude);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 13));

        map.addMarker(new MarkerOptions()
                .title("Current Location")
                .snippet("Most Recent Location")
                .position(CurrentLocation));
    }

    void ShareLink(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getLink()));
        startActivity(browserIntent);
    }

    void SendSMS(){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(data.getPhone(), null, data.printString(), sentPI, deliveredPI);
    }

    void ShareDetail(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String detail=data.printString();
        sendIntent.putExtra(Intent.EXTRA_TEXT, detail);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


}
