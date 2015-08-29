package science.inc.com.news;


import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements JsonAsyncTask.showNewsData {

    ExpandableListView expandableListView;
    ArrayList<NewsData> newsData;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    NewsFragment newsFragment=null;
    static int groupPosition=0;
    static int childPosition=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        newsFragment=new NewsFragment();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, newsFragment).commit();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#AB47BC")));
        setDrawerListener();
        prepareData();

    }

    void setDrawerListener(){
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.mipmap.ic_launcher, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    void prepareData(){
        newsData=new ArrayList<NewsData>();
        JsonAsyncTask jt= new JsonAsyncTask(MainActivity.this,newsData);
        jt.execute();
        jt.setInterface(MainActivity.this);
    }

    @Override
    public void setNews() {
        expandableListView.setAdapter(new DataListAdapter(MainActivity.this, newsData));



        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                setTitle(newsData.get(groupPosition).getCategoryName());
                MainActivity.childPosition = childPosition;
                MainActivity.groupPosition = groupPosition;
                displayView();
                return false;
            }
        });
        displayView();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    void displayView(){


            Set<String> s= newsData.get(groupPosition).getSubcategories().keySet();
            int count=0;
            String string=null;
            for(String str :s)
            {
                if(count==childPosition)
                    string=str;
                count++;
            }
            String imageUrl=newsData.get(groupPosition).getSubcategories().get(string);

            Picasso.with(this)
                    .load(imageUrl).placeholder( R.drawable.progress_animation).error(R.drawable.error)
                    .into(newsFragment.image);
        Toast.makeText(MainActivity.this, imageUrl, Toast.LENGTH_LONG).show();
            setTitle(newsData.get(groupPosition).getCategoryName());
            newsFragment.text.setText(string);
            newsFragment.description.setText(newsData.get(groupPosition).getSubChildDescription().get(childPosition));
        }

}

