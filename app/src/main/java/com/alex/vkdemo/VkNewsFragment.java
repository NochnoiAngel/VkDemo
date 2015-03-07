package com.alex.vkdemo;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alex.vkdemo.adapter.VkNewsCardsAdapter;
import com.alex.vkdemo.dummy.VkNewsItem;
import com.alex.vkdemo.model.VkNewsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;



/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class VkNewsFragment extends DemoFragment implements  SwipeRefreshLayout.OnRefreshListener, VkNewsCardsAdapter.OnItemClickListener {



    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */


    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recList;
    private VkNewsCardsAdapter cardsAdapter;
    private LinearLayoutManager llm;
    public VkNewsResponse newsResponse;
    private String nextFrom;
    private int toDate;
    private boolean refreshing;
    //////
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean loading;

    public static VkNewsFragment newInstance() {
        VkNewsFragment fragment = new VkNewsFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VkNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onResume() {


        super.onResume();
    }

    private boolean existsVkToken() {
      return   ((VkActivity)getActivity()).existsVkToken();
    }

    private void addVkNews(VkNewsResponse responseModel) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vknews_grid, container, false);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        if (existsVkToken()){
            loadNews();
        }
        refreshLayout.setOnRefreshListener(this);
        recList = (RecyclerView) view.findViewById(R.id.listView);

        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        createNewsResponse();
        cardsAdapter=new VkNewsCardsAdapter(getActivity(),recList,newsResponse);
        recList.setAdapter(cardsAdapter);
        cardsAdapter.setOnItemClickListener(this);
        loading=false;
        recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = llm.getChildCount();
                totalItemCount = llm.getItemCount();
                pastVisiblesItems = llm.findFirstVisibleItemPosition();

                if (!loading) {
                    if ( (visibleItemCount+pastVisiblesItems) >= totalItemCount) {
                      loadMore();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    private void loadMore() {
        loading=true;
        VKRequest request= VKApi.news().get(nextFrom);
        refreshLayout.setRefreshing(true);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {

                String responseString=response.responseString;
                Gson gson=new GsonBuilder().create();
                VkNewsResponse responseModel=gson.fromJson(responseString,VkNewsResponse.class);
                addNews(responseModel, false);
                VkNewsFragment.this.nextFrom=responseModel.response.next_from;
                refreshLayout.setRefreshing(false);
                loading=false;
                super.onComplete(response);
            }

            @Override
            public void onError(VKError error) {
                loading=false;
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }

    private void createNewsResponse() {
        newsResponse=new VkNewsResponse();
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




    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */


    @Override
    public void onRefresh() {
        if (refreshing)
            return;
        refreshing=true;
        VKRequest request= VKApi.news().get(toDate);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                String responseString=response.responseString;
                Gson gson=new GsonBuilder().create();
                VkNewsResponse responseModel=gson.fromJson(responseString,VkNewsResponse.class);
               addNews(responseModel,true);
                refreshLayout.setRefreshing(false);
                refreshing=false;
                super.onComplete(response);
            }

            @Override
            public void onError(VKError error) {
                refreshing=false;
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }
    public void loadNews() {
        refreshing=true;
        VKRequest request= VKApi.news().get();
        refreshLayout.setRefreshing(true);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                String responseString=response.responseString;
                Gson gson=new GsonBuilder().create();
                VkNewsResponse responseModel=gson.fromJson(responseString,VkNewsResponse.class);
                addNews(responseModel, false);
                VkNewsFragment.this.toDate=responseModel.get(0).getDescription().getDate();
                refreshLayout.setRefreshing(false);
                refreshing=false;
                super.onComplete(response);
            }

            @Override
            public void onError(VKError error) {
                refreshing=false;
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }
    private void addNews(VkNewsResponse responseModel, boolean toStart) {
        if (this.newsResponse!=null)
            this.newsResponse.merger(responseModel,toStart);
        cardsAdapter.notifyDataSetChanged();
        nextFrom=responseModel.response.next_from;
    }

    @Override
    public void onItemClick(RecyclerView adapterView, View itemView, int position, long itemId) {
        mListener.onClickNews(newsResponse.get(position));

    }


    public interface OnFragmentInteractionListener {


        void onClickNews(VkNewsItem vkNewsItem);
    }

}
