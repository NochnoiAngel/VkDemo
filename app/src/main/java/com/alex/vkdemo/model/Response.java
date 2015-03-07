package com.alex.vkdemo.model;

import com.alex.vkdemo.dummy.VkNewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marat on 01.03.15.
 */
public class Response
{
    public List<Item> items ;
    public List<Profile> profiles ;
    public List<Group> groups ;
    public String next_from ;
    private int count;

    public int getCount() {
        if (items==null)
        return 0;
        else
            return items.size();

    }

    public VkNewsItem get(int position) {

        if (items!=null)
        {  Item  item= items.get(position);
            VkNewsItem newsItem=new VkNewsItem(item);
            newsItem.setDescription(getDescription(item));
            return newsItem;
        }
        return null;
    }
    public NewsDesc getDescription(Item item){
       if (item.source_id<0){
        return    getGroupDescription(item);
       }
        else {
           return    getProfileDescription(item);
       }
    }

    private NewsDesc getProfileDescription(Item item) {
        for (Profile p:profiles){
            if (p.id==item.source_id){

                return new NewsDesc(p.getDisplayName(),item.likes.count,p.photo_50,item.date);
            }

        }
        return null;
    }

    private NewsDesc getGroupDescription(Item item) {

        for (Group g:groups){
        if (g.id==-item.source_id){

            return new NewsDesc(g.name,item.likes.count,g.photo_50,item.date);
        }

        }
        return null;

    }

    public void merger(Response response, boolean toStart) {
        if (response.items!=null){
                if (!toStart)
             items.addAll(response.items);
        else
              items.addAll(0,response.items);
        }
        else {

        }
        addGroups(response.groups);
        addProfiles(response.profiles);
        count+=response.count;
    }

    private void addGroups(List<Group> groups) {
        if (groups!=null){
            if (this.groups==null)
                this.groups=new ArrayList<>();
        for (Group newGroup:groups)
        {
            boolean exists=false;
            for (Group exGroup:this.groups)
            {
             if (exGroup.id==newGroup.id){
                 exists=true;
             }
            }
            if (exists)
                this.groups.add(newGroup);
        }
        }
    }

    private void addProfiles(List<Profile> profiles) {
        if (profiles!=null){
            if (this.profiles==null)
                this.profiles=new ArrayList<>();
        for (Profile newProfile:profiles)
        {
            boolean exists=false;
            for (Profile exProfile:this.profiles)
            {
                if (exProfile.id==newProfile.id){
                    exists=true;
                }
            }
            if (!exists)
                this.profiles.add(newProfile);
        }
        }
    }
}
