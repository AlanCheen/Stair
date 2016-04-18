package yfy.github.stair.data;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yfy.github.stair.R;
import yfy.github.stair.adapters.GankMeiziAdapter;
import yfy.github.stair.api.GankClient;
import yfy.github.stair.base.BaseFragment;
import yfy.github.stair.ui.GankDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeiziFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeiziFragment extends BaseFragment {


    public static final String TAG = "GankAndroidFragment";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private GankMeiziAdapter mAdapter;

    private int mCurrPage = 1;
    private ArrayList<GankEntity> mDatas;
    private boolean canLoadmore;

    public static MeiziFragment newInstance() {
        MeiziFragment fragment = new MeiziFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_meizi;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mDatas = new ArrayList<>();
        mAdapter = new GankMeiziAdapter(mActivity, mDatas);

        setHasOptionsMenu(true);
        setupRv();
        requestData();
    }

    private void requestData() {
        Observable<GAndroid> android = GankClient.getIns().getMeizi(mCurrPage);
        android.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GAndroid>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        mRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
                        mRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onNext(GAndroid gAndroid) {
                        List<GankEntity> entities = gAndroid.results;

                        canLoadmore = entities.size() >= 0;

                        if (mCurrPage == 1) {
                            mDatas.clear();
                        }
                        mDatas.addAll(entities);
                        mAdapter.notifyDataSetChanged();
                        Log.d(TAG, "onNext() called with: " + "gAndroid = [" + gAndroid + "]");
                    }
                });
    }


    private void setupRv() {

        mRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(() -> {
            mCurrPage = 1;
            requestData();
        });

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity,1);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (canLoadmore && !mRefreshLayout.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] == mDatas.size() - 1) {
                    if (canLoadmore && !mRefreshLayout.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPosition() == mDatas.size() - 1) {
                        mRefreshLayout.setRefreshing(true);
                        mCurrPage++;
                        requestData();
                    }
                }
            }
        });

        mAdapter.setOnItemClickListener(new GankMeiziAdapter.onItemClickListener() {
            @Override
            public void onItemClick(GankMeiziAdapter.GankViewHolder holder, GankEntity entity) {

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, holder.getIvMeizi(), getString(R.string.meizi_transition_name));


                Intent intent = GankDetailActivity.creatIntent(mActivity, entity);

//                MeiziFragment.this.startActivity(intent);

                ActivityCompat.startActivity(mActivity, intent,options.toBundle());
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu() called with: " + "menu = [" + menu + "], inflater = [" + inflater + "]");
//        super.onCreateOptionsMenu(menu, inflater);

    }
}
