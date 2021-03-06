package yfy.github.stair.ui;

import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import yfy.github.stair.R;
import yfy.github.stair.base.BaseActivity;

/**
 * 有toolbar的 统一继承这个activity
 *
 * 统一:
 * 设置toolbar
 * 设置title
 * 处理左上返回按钮事件
 */
public abstract class ToolbarActivity extends BaseActivity {

    @Nullable
    @Bind(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupToolbar();
    }

    protected void setupToolbar() {
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //统一设置 title
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        updateTitle(title);
    }
    protected void updateTitle(CharSequence title) {
        if (null != mCollapsingToolbar) {
            mCollapsingToolbar.setTitle(title);
        } else if (null != mToolbar) {
            mToolbar.setTitle(title);
        }
    }

}
