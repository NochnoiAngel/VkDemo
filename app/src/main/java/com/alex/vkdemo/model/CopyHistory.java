package com.alex.vkdemo.model;

import java.util.List;

public class CopyHistory
{
    public int id ;
    public int owner_id ;
    public int from_id ;
    public int date ;
    public String post_type ;
    public String text ;
    public List<Attachment> attachments ;
    public PostSource post_source ;
    public int signer_id ;
}