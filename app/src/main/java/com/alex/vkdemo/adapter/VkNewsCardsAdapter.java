package com.alex.vkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.vkdemo.R;
import com.alex.vkdemo.dummy.VkNewsItem;
import com.alex.vkdemo.model.Attachment;
import com.alex.vkdemo.model.NewsDesc;
import com.alex.vkdemo.model.VkNewsResponse;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class VkNewsCardsAdapter extends RecyclerView.Adapter<VkNewsCardsAdapter.ContactViewHolder> {

    private final Context context;
    private final RecyclerView adapterView;
    private final  VkNewsResponse contactList;
    public static final int NO_IMAGE=0;
    public static final int ONE_IMAGE=1;
    public static final int TWE_IMAGE=2;

    private OnItemClickListener itemClickListener;
    private LayoutInflater inflater;
    public VkNewsCardsAdapter(Context context,RecyclerView adapterView,VkNewsResponse contactList) {

            this.context=context;
        this.contactList = contactList;
        this.adapterView=adapterView;
        inflater= LayoutInflater.from(context);

    }
    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.itemClickListener=clickListener;
    }
    @Override
    public int getItemCount() {
          return contactList.getNewsCount();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, final int i) {
        VkNewsItem ci = contactList.get(i);
        if (itemClickListener!=null)
        {
            final View itemView=contactViewHolder.view;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(adapterView,itemView,i,getItemId(i));
                }
            });
        }
       if(ci.item!=null)
           if (!TextUtils.isEmpty(ci.item.text)) {
               contactViewHolder.newsText.setText(ci.item.text);
               contactViewHolder.newsText.setVisibility(View.VISIBLE);
           }
             else
                contactViewHolder.newsText.setVisibility(View.GONE);
        NewsDesc desc = ci.getDescription();
        contactViewHolder.newsTitle.setText(desc.getTitle());
        if (desc.getLikeCount()==0)
            contactViewHolder.newsLikeCount.setVisibility(View.GONE);
        else
            contactViewHolder.newsLikeCount.setVisibility(View.VISIBLE);
        contactViewHolder.newsLikeCount.setText(String.valueOf(desc.getLikeCount()));
        contactViewHolder.newsPostDate.setText(String.valueOf(desc.getPostDate()));
        ImageLoader imageLoader = ImageLoader.getInstance();

        View imagesView=null;
        ViewGroup contener=null;
        int type= ci.getImageCount();
        if (type>2){
            type=1;
        }
        switch (type){
            case TWE_IMAGE:


            case ONE_IMAGE:
                if (imagesView==null) {
                    contener = (ViewGroup) contactViewHolder.view.findViewById(R.id.im_content_contener);
                    contener.setVisibility(View.VISIBLE);
                    if (contener.getChildCount()==0) {
                        imagesView = inflater.inflate(R.layout.im_one_image, (ViewGroup) contactViewHolder.view, false);
                        contener = (ViewGroup) contactViewHolder.view.findViewById(R.id.im_content_contener);
                        contener.addView(imagesView);
                    }
                    else {
                        imagesView=contener;
                    }
                }
                ImageView firstImage= (ImageView) imagesView.findViewById(R.id.first_image);
                Attachment image = ci.getImage(1);
                imageLoader.displayImage(image.photo.photo_604,firstImage);
                break;
            case NO_IMAGE:
                contener = (ViewGroup) contactViewHolder.view.findViewById(R.id.im_content_contener);
                contener.setVisibility(View.GONE);
                break;

        }

        imageLoader.displayImage(desc.getPhotoUrl(), contactViewHolder.newsIcon);

   }

   @Override
   public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView =inflater.
                    inflate(R.layout.news_card_item, viewGroup, false);

        return new ContactViewHolder(itemView);
   }

    /*@Override
    public int getItemViewType(int position) {
        VkNewsItem item = contactList.get(position);
        int imageCount=item.getImageCount();


        switch (imageCount)
        {
            case 0:
                return NO_IMAGE;
            case 1:
                return ONE_IMAGE;

            case 2:
                return TWE_IMAGE;
        }
        return TWE_IMAGE;
    }*/


    @Override
    public long getItemId(int position) {
        return contactList.get(position).id;
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        private  FrameLayout imContentContener;
        private  TextView newsPostDate;
        private  TextView newsLikeCount;
        protected  TextView newsTitle;
        protected TextView newsText;
        protected ImageView newsIcon;
            protected View view;
        public ContactViewHolder(View v) {
            super(v);
            this.view=v;
            newsText =  (TextView) v.findViewById(R.id.news_text);
            newsTitle= (TextView) v.findViewById(R.id.news_title);
            newsIcon= (ImageView) v.findViewById(R.id.news_icon);
            newsLikeCount= (TextView) v.findViewById(R.id.like_count);
            newsPostDate= (TextView) v.findViewById(R.id.post_date);
            imContentContener = (FrameLayout) v.findViewById(R.id.im_content_contener);

        }
    }

    public interface OnItemClickListener {

        void onItemClick(RecyclerView adapterView, View itemView, int i, long itemId);
    }

}