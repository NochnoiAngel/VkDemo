package com.alex.vkdemo.model;

/**
 * Created by marat on 01.03.15.
 */
public class Profile
{
    public int id ;
    public String first_name ;
    public String last_name ;
    public int sex ;
    public String screen_name ;
    public String photo_50 ;
    public String photo_100 ;
    public int online ;
    public String online_app ;
    public int online_mobile ;
    private String displayName;

    public String getDisplayName() {
        if (displayName!=null)
            return displayName;
        if (first_name!=null&&last_name!=null){
            return String.format("%s %s",first_name,last_name);
        }
        if (first_name!=null)
            return first_name;
        if (last_name!=null)
            return last_name;
        return displayName;
    }
}
