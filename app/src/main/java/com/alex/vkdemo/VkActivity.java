package com.alex.vkdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.alex.vkdemo.dummy.VkNewsItem;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;


public class VkActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        VkNewsFragment.OnFragmentInteractionListener ,VkNewsItemFragment.OnFragmentInteractionListener{

    private static final int APP_STATE_SHOW_ITEM = 1;
    private static final int APP_STATE_SHOW_LIST = 2;
    public static String VkKey="n2Hco2rCuBQ459qDqafu";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private VKAccessToken vkAccessToken;


    private static final int VkLoginRequestCode=1;
    private VKSdkListener vkSdkListener=new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {

        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {

        }

        @Override
        public void onAccessDenied(VKError authorizationError) {

        }
    };
    private VkNewsFragment newsFragment;
    private VkNewsItemFragment newsItemFragment;
    //private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private int STATE;


    public VKAccessToken getVkAccessToken(){
        return vkAccessToken;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VKUIHelper.onCreate(this);
        setContentView(R.layout.activity_vk);

         toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        vkAccessToken= VKAccessToken.tokenFromSharedPreferences(this, B.VkTokenKey);
        if (vkAccessToken==null)
        {
            startLoginActivity();
        }
        else
        {
            VKSdk.initialize(vkSdkListener, "4799945", vkAccessToken);

        }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();



        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
               (DrawerLayout) findViewById(R.id.drawer_layout));



    }

    private void startLoginActivity() {
       Intent intent=new Intent(this,LoginActivity.class);
       startActivityForResult(intent,VkLoginRequestCode);
    }

    @Override
    public boolean onSupportNavigateUp() {

        return super.onSupportNavigateUp();
    }
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        //toggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//     //   toggle.onConfigurationChanged(newConfig);
//    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switch (position){
            case 0:
                FragmentManager fragmentManager = getSupportFragmentManager();
                newsFragment=VkNewsFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,newsFragment )
                        .commit();
                STATE=APP_STATE_SHOW_LIST;
                break;
            case 1:
                VKAccessToken.removeTokenAtKey(this,B.VkTokenKey);
                startLoginActivity();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.vk, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case VkLoginRequestCode:
                if (Activity.RESULT_CANCELED==resultCode){
                    finish();
                }
                else{
                    vkAccessToken=VKAccessToken.tokenFromSharedPreferences(this,B.VkTokenKey);
                    newsFragment.loadNews();
                }
                break;

        }

    }





    @Override
    public void onClickNews(VkNewsItem vkNewsItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        newsItemFragment=VkNewsItemFragment.newInstance(vkNewsItem);
        STATE=APP_STATE_SHOW_ITEM;
        fragmentManager.beginTransaction().
                add(R.id.container,newsItemFragment).hide(newsFragment).
                commit();

     // mDrawerToggle =  mNavigationDrawerFragment.getmDrawerToggle();
      //  toolbar.setNavigationIcon(android.R.drawable.ic_menu_add);
        //TODO
       //toolbar.set
     //   mDrawerToggle.setDrawerIndicatorEnabled(true);
        //newsFragment=VkNewsItemFragment.newInstance();
       // fragmentManager.beginTransaction()
         //       .replace(R.id.container,newsFragment )
           //     .commit();
    }

    @Override
    public void onBackPressed() {

        if (STATE==APP_STATE_SHOW_ITEM){
            FragmentManager fragmentManager = getSupportFragmentManager();
            STATE=APP_STATE_SHOW_ITEM;
            fragmentManager.beginTransaction().
                    remove(newsItemFragment).show(newsFragment).
                    commit();
        }
        else {
            super.onBackPressed();
        }
    }

    public boolean existsVkToken() {
        return vkAccessToken!=null;
    }


    /**
     * A placeholder fragment containing a simple view.
     */


}
