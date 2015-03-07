package com.alex.vkdemo;

import android.support.v4.app.Fragment;

import com.vk.sdk.VKAccessToken;

/**
 * Created by marat on 28.02.15.
 */
public class DemoFragment extends Fragment {
    public VKAccessToken getVkAccessToken(){
        return ((VkActivity)getActivity()).getVkAccessToken();
    }
}
