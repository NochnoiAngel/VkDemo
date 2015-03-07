package com.alex.vkdemo.model;

import java.util.List;

/**
 * Created by marat on 01.03.15.
 */
public class Item
{
    public String type ;
    public int source_id ;
    public int date ;
    public int post_id ;
    public String post_type ;
    public String text ;
    public List<Attachment> attachments ;
    public PostSource post_source ;
    public Comments comments ;
    public Likes likes ;
    public Reposts reposts ;
    public int signer_id ;
    public List<CopyHistory> copy_history ;

    public Attachment getImage(int position) {
       for (int i=0;i<attachments.size();i++){
          Attachment atach=attachments.get(i);
         if (  atach.type.equals("photo"))
         {
             position--;
         }
          if (position==0)
           return atach;
       }
        return null;
    }

    public int getImageCount() {


        if (attachments==null)
            return 0;
        int count=0;
        for (int i=0;i<attachments.size();i++){
            Attachment atach=attachments.get(i);
            if (  atach.type.equals("photo"))
                count++;
        }
        return count;
    }
}
