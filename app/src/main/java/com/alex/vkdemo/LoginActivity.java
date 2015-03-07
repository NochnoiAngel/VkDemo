package com.alex.vkdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;


public class LoginActivity extends ActionBarActivity {
    private VKSdkListener vkSdkListener=new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {

        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            expiredToken.saveTokenToSharedPreferences(LoginActivity.this,B.VkTokenKey);
            setResult(Activity.RESULT_OK);
            finish();

        }

        @Override
        public void onAccessDenied(VKError authorizationError) {

        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            token.saveTokenToSharedPreferences(LoginActivity.this,B.VkTokenKey);
            super.onAcceptUserToken(token);
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            super.onReceiveNewToken(newToken);
            newToken.saveTokenToSharedPreferences(LoginActivity.this,B.VkTokenKey);
            setResult(Activity.RESULT_OK);
            finish();
        }

        @Override
        public void onRenewAccessToken(VKAccessToken token) {
            super.onRenewAccessToken(token);
        }


    };
    private VKAccessToken vkAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VKUIHelper.onCreate(this);
        setContentView(R.layout.activity_login);
        vkAccessToken= VKAccessToken.tokenFromSharedPreferences(this, B.VkTokenKey);

        VKSdk.initialize(vkSdkListener, "4799945", vkAccessToken);

        login();

    }

    private void login() {
        VKSdk.authorize(VKScope.get(VKScope.WALL,VKScope.FRIENDS), true, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_login, menu);
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


    public void onLoginClick(View view) {
       login();
    }
}
