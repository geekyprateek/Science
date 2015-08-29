package science.inc.com.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by prateek on 8/28/2015.
 */
public class NewsFragment extends Fragment {
    Context mContext;
    public ImageView image;
    public TextView text;
    public TextView description;
    @Override
    public void onAttach(Context context) {
        this.mContext=context;
        super.onAttach(context);
    }

    public NewsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_layout,container,false);
        image=(ImageView)view.findViewById(R.id.image);
        text=(TextView)view.findViewById(R.id.text);
        description=(TextView)view.findViewById(R.id.description);
        return view;
    }
}
