package yfy.github.stair.data;

import android.os.Bundle;

import yfy.github.stair.R;
import yfy.github.stair.ui.BaseFragment;

/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/11/23
 */
public class ImportNewFragment extends BaseFragment {

//    @Bind(R.id.recycler_view)
//    RecyclerView mRecyclerView;
//    @Bind(R.id.refresh_layout)
//    SwipeRefreshLayout mRefreshLayout;

    public ImportNewFragment() {
    }

    public static ImportNewFragment newInstance() {
        ImportNewFragment fragment = new ImportNewFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.fragment_import_new;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

}
