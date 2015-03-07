package com.vk.sdk.api.methods;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKParser;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VkApiNews;

import org.json.JSONObject;

/**
 * Created by marat on 28.02.15.
 */
public class VKApiNewsFeed  extends VKApiBase{
    public static String filters="filters";
    public static String return_banned="return_banned";
    public static String start_time="start_time";
    public static String end_time="end_time";
    public static String max_photos="max_photos";
    public static String source_ids="source_ids";
    public static String start_from="start_from";
    public static String count="count";
    public static String fields="fields";
    public VKRequest get(long start_time,long end_time,int start_from){
        return  get(start_time,end_time,100,2,start_from);
    }

    public VKRequest get() {
        return get(VKParameters.from(
                VKApiNewsFeed.filters,"post"

        ));
    }
   public VKRequest get(int fromDate){
        return get(VKParameters.from(
                VKApiNewsFeed.filters,"post",
                VKApiNewsFeed.start_time, fromDate
                ));
    }
    public VKRequest get(long start_time,long end_time,int max_photos,int count,int start_from) {
        return get(VKParameters.from(
                VKApiNewsFeed.filters, "post",
                VKApiNewsFeed.start_time, start_time,
                VKApiNewsFeed.end_time, end_time,
                VKApiNewsFeed.max_photos, max_photos,
                VKApiNewsFeed.count, count,
                VKApiNewsFeed.start_from, start_from));
    }

    /**
     * https://vk.com/dev/users.get
     *
     * @param params use parameters from description with VKApiConst class
     * @return Request for load
     */
    public VKRequest get(VKParameters params) {
        return prepareRequest("get", params);
    }

    public VKRequest get(String nextFrom) {
        return get(VKParameters.from(
                VKApiNewsFeed.filters, "post",
                VKApiNewsFeed.start_from, nextFrom));
    }
}
