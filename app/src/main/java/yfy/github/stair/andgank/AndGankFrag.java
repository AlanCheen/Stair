package yfy.github.stair.andgank;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import yfy.github.stair.R;
import yfy.github.stair.adapters.GankAdapter;
import yfy.github.stair.base.BaseFragment;
import yfy.github.stair.data.DividerItemDecoration;
import yfy.github.stair.data.GankEntity;
import yfy.github.stair.ui.WebActivity;

/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/11/23
 */
public class AndGankFrag extends BaseFragment implements AndGankContract.View{

    public static final String TAG = "GankAndroidFragment";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;


    private AndGankPresenter mPresenter;
    private GankAdapter mAdapter;

    private int mCurrPage = 1;
    private ArrayList<GankEntity> mDatas;
    private boolean canLoadmore;

    public AndGankFrag() {
        // Required empty public constructor
    }

    public static AndGankFrag newInstance() {
        AndGankFrag fragment = new AndGankFrag();
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

        mPresenter = new AndGankPresenter(this);

        setupRv();
        requestData();
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


    private void requestData() {
        mPresenter.requestData(mCurrPage);
    }

    @OnClick({R.id.fab})
    public void onFabClick(View v){
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void showData(List<GankEntity> entities) {
        if (null == entities) {
            return ;
        }
        canLoadmore = entities.size() >= 0;

        if (mCurrPage == 1) {
            mDatas.clear();
        }
        mDatas.addAll(entities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(mFab, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean isLoading) {
        mRefreshLayout.setRefreshing(isLoading);
    }
}
