package science.inc.com.news;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by prateek.g2 on 27-Aug-15.
 */
public class DataListAdapter implements ExpandableListAdapter {

    Activity mContext;
    ArrayList<NewsData> newsData;
    DataListAdapter(Activity context,ArrayList<NewsData> newsData){
        this.newsData=newsData;
        mContext=context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return newsData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return newsData.get(groupPosition).getSubcategories().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return newsData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Set<String>s= newsData.get(groupPosition).getSubcategories().keySet();
        int count=0;
        String string=null;
        for(String str :s)
        {
            if(count==childPosition)
                string=str;
            count++;
        }

        return string;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView=((MainActivity)mContext).getLayoutInflater().inflate(android.R.layout.simple_expandable_list_item_1,
                            parent, false);
        }
        TextView tv=((TextView)convertView.findViewById(android.R.id.text1));
        tv.setTextSize(20);

        tv.setText(newsData.get(groupPosition).getCategoryName());
        return(convertView);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView=
                    ((MainActivity)mContext).getLayoutInflater().inflate(android.R.layout.simple_expandable_list_item_1,
                            parent, false);
        }
        TextView tv=((TextView)convertView.findViewById(android.R.id.text1));
        tv.setTextSize(15);
        tv.setText((String)getChild(groupPosition,childPosition));
        return(convertView);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        Toast.makeText(mContext,String.valueOf(groupPosition),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Toast.makeText(mContext,String.valueOf(groupPosition),Toast.LENGTH_LONG).show();
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }




    }

