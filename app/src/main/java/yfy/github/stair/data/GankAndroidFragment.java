package yfy.github.stair.data;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yfy.github.stair.R;
import yfy.github.stair.adapters.GankAdapter;
import yfy.github.stair.api.GankClient;
import yfy.github.stair.base.BaseFragment;
import yfy.github.stair.ui.WebActivity;
import yfy.github.stair.utils.Log;

/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/11/23
 */
public class GankAndroidFragment extends BaseFragment {

    public static final String TAG = "GankAndroidFragment";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    private GankAdapter mAdapter;

    private int mCurrPage = 1;
    private ArrayList<GankEntity> mDatas;
    private boolean canLoadmore;

    public GankAndroidFragment() {
        // Required empty public constructor
    }

    public static GankAndroidFragment newInstance() {
        GankAndroidFragment fragment = new GankAndroidFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_gank_android;
    }


    @Override
    protected void init(Bundle savedInstanceState) {

        mDatas = new ArrayList<>();
        mAdapter = new GankAdapter(mActivity, mDatas);

        setupRv();
        requestData();
    }

    private void requestData() {
        mRefreshLayout.setRefreshing(true);
        Observable<GAndroid> android = GankClient.getIns().getAndroid(mCurrPage);
        android
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GAndroid>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError() called with: " + "e = [" + e + "]", e);
                        mRefreshLayout.setRefreshing(false);
                        Snackbar.make(mFab, "onError:" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
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
        mRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent);

        mRefreshLayout.setOnRefreshListener(() -> {
            mCurrPage = 1;
            requestData();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (canLoadmore && !mRefreshLayout.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPosition() == mDatas.size() - 1) {
                        mRefreshLayout.setRefreshing(true);
                        mCurrPage++;
                        requestData();
                    }
                }
            }
        });

        mAdapter.setOnItemClickListener(entity -> {
            Intent intent = WebActivity.creatIntent(mActivity, entity.url);
            startActivity(intent);
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

    }
}
