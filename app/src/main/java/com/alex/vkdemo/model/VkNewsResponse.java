package com.alex.vkdemo.model;

import com.alex.vkdemo.dummy.VkNewsItem;

/**
 * Created by marat on 01.03.15.
 */
public class VkNewsResponse
{
    public Response response ;

    public int getNewsCount(){
        if (response==null)
        return  0;
        else
            return response.getCount();
    }

    public VkNewsItem get(int position) {
            if (response==null)
                return null;
        else
        return response.get(position);
    }


    public void merger(VkNewsResponse responseModel) {
   merger(responseModel,false);
    }
    public void merger(VkNewsResponse responseModel,boolean toStart) {
        if (response==null)
            this.response=responseModel.response;
        else{
            response.merger(responseModel.response, toStart);
        }
    }
}
