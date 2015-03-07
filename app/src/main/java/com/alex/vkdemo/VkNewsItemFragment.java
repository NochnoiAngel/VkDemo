package com.alex.vkdemo;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.vkdemo.dummy.VkNewsItem;
import com.alex.vkdemo.model.Attachment;
import com.alex.vkdemo.model.NewsDesc;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VkNewsItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VkNewsItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VkNewsItemFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private VkNewsItem vkNewsItem;
    private TextView newsText;
    private TextView newsTitle;
    private ImageView newsIcon;
    private TextView newsLikeCount;
    private TextView newsPostDate;
    private FrameLayout imContentContener;
    public static final int NO_IMAGE=0;
    public static final int ONE_IMAGE=1;
    public static final int TWE_IMAGE=2;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     */
    // TODO: Rename and change types and number of parameters
    public static VkNewsItemFragment newInstance(VkNewsItem item) {
        VkNewsItemFragment fragment = new VkNewsItemFragment();
        fragment.setVkNewsItem(item);
        return fragment;
    }

    public VkNewsItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_news_item, container, false);
        this.inflater=inflater;
        newsText =  (TextView) v.findViewById(R.id.news_text);
        newsTitle= (TextView) v.findViewById(R.id.news_title);
        newsIcon= (ImageView) v.findViewById(R.id.news_icon);
        newsLikeCount= (TextView) v.findViewById(R.id.like_count);
        newsPostDate= (TextView) v.findViewById(R.id.post_date);
        imContentContener = (FrameLayout) v.findViewById(R.id.im_content_contener);


        if(vkNewsItem.item!=null)
            if (!TextUtils.isEmpty(vkNewsItem.item.text))
                newsText.setText(vkNewsItem.item.text);
               else
                newsText.setVisibility(View.GONE);
        newsText.setMaxLines(vkNewsItem.item.text.length());
        NewsDesc desc = vkNewsItem.getDescription();
        newsTitle.setText(desc.getTitle());
        if (desc.getLikeCount()==0)
            newsLikeCount.setVisibility(View.GONE);
        newsLikeCount.setText(String.valueOf(desc.getLikeCount()));
        newsPostDate.setText(String.valueOf(desc.getPostDate()));
         imageLoader = ImageLoader.getInstance();
   /*     int type= vkNewsItem.getImageCount();
        View imagesView=null;
       switch (type){
            case TWE_IMAGE:


            case ONE_IMAGE:
              if (imagesView==null)
              {

                  imagesView=  inflater.inflate(R.layout.im_one_image,(ViewGroup)v);
                  ViewGroup contener= (ViewGroup) imagesView.findViewById(R.id.im_content_contener);
                contener.addView(imagesView,0);
              }


                ImageView firstImage= (ImageView) imagesView.findViewById(R.id.first_image);
                Attachment image = vkNewsItem.getImage(1);
                imageLoader.displayImage(image.photo.photo_604,firstImage);
                break;


        }*/

        imageLoader.displayImage(desc.getPhotoUrl(), newsIcon);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        int type= vkNewsItem.getImageCount();
        if (type>2){
            type=2;
        }
        View imagesView=null;
        ViewGroup contener=null;
        switch (type){
            case TWE_IMAGE:





                ImageView second_image= (ImageView) imagesView.findViewById(R.id.second_image);
                Attachment second = vkNewsItem.getImage(2);
                imageLoader.displayImage(second.photo.photo_604,second_image);

            case ONE_IMAGE:
                if (imagesView==null) {
                    imagesView = inflater.inflate(R.layout.im_one_image, (ViewGroup) view, false);
                    contener = (ViewGroup) view.findViewById(R.id.im_content_contener);
                    contener.addView(imagesView);
                }
                ImageView firstImage= (ImageView) imagesView.findViewById(R.id.first_image);
                Attachment image = vkNewsItem.getImage(1);
                imageLoader.displayImage(image.photo.photo_604,firstImage);
                break;


        }


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setVkNewsItem(VkNewsItem vkNewsItem) {
        this.vkNewsItem = vkNewsItem;
    }

    public VkNewsItem getVkNewsItem() {
        return vkNewsItem;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

    }

}
