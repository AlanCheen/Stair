package yfy.github.stair.data;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yfy.github.stair.R;
import yfy.github.stair.adapters.GankDailyAdapter;
import yfy.github.stair.api.GankClient;
import yfy.github.stair.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GankDailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GankDailyFragment extends BaseFragment {


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private GankDailyAdapter mAdapter;

    private int mCurrPage = 1;
    private ArrayList<GankDaily> mDatas;
    private boolean canLoadmore;

    public GankDailyFragment() {
        // Required empty public constructor
    }

    public static GankDailyFragment newInstance() {
        GankDailyFragment fragment = new GankDailyFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_gank_daily;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mDatas = new ArrayList<>();
        mAdapter = new GankDailyAdapter(mActivity, mDatas);
        setupRv();
        requestData();
    }


    private void setupRv() {

        mRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(() -> {
            mCurrPage = 1;
            requestData();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

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
//            Intent intent = WebActivity.creatIntent(mActivity, entity.url);
//            startActivity(intent);
        });

    }
    //// TODO: 15/11/24 处理年月日数据
    private void requestData() {
        Observable<GankDaily> daily = GankClient.getIns().getDaily(2015, 11, 20);
        daily.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<GankDaily>() {
            @Override
            public void onCompleted() {
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(GankDaily gankDaily) {

//                canLoadmore = entities.size()>=0;

                if (mCurrPage == 1) {
                    mDatas.clear();
                }
                mDatas.add(gankDaily);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

}
