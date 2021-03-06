package yfy.github.stair.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;


/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/10/8
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG = "BaseActivity";
    protected Context mContext;
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = provideLayoutId();
        if (layoutId > 0) {
            setContentView(layoutId);
            ButterKnife.bind(this);
        }
        mContext = this;
        init(savedInstanceState);
    }

    protected abstract @LayoutRes int provideLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
