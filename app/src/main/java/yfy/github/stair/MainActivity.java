package yfy.github.stair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;
import yfy.github.stair.andgank.AndGankFrag;
import yfy.github.stair.data.GankDailyFragment;
import yfy.github.stair.data.ImportNewFragment;
import yfy.github.stair.data.MeiziFragment;
import yfy.github.stair.base.BaseFragment;
import yfy.github.stair.ui.ToolbarActivity;


/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/10/8
 */
public class MainActivity extends ToolbarActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected static final String TAG = "MainActivity";

    @IntDef({PAGE_GANK_ANDROID, PAGE_IMPORT_NEW, PAGE_GANK_DAILY, PAGE_GANK_MEIZI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Page {
    }

    public static final int PAGE_GANK_ANDROID = 1;
    public static final int PAGE_IMPORT_NEW = 2;
    public static final int PAGE_GANK_DAILY = 3;
    public static final int PAGE_GANK_MEIZI = 4;

    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private BaseFragment mGankAndroidFragment;
    private BaseFragment mImportNewFragment;
    private BaseFragment mGankDailyFragment;
    private BaseFragment mGankMeiziFragment;

    @Page
    private int mCurrPage;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        if (null == savedInstanceState) {
            changePage(PAGE_GANK_ANDROID);
        }


        initBadge();
    }


    private TextView mTvBadge;
    private void initBadge() {

//        MenuItemCompat.getActionView(mNavView.getMenu().findItem())
        View news = mNavView.getMenu().findItem(R.id.news).getActionView();
        mTvBadge = (TextView) news.findViewById(R.id.tv_badge);
        mTvBadge.setText("99+");
    }



    private void changePage(@Page int page) {
        if (mCurrPage == page) {
            return;
        }
        mCurrPage = page;
        hideAll();

        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();

        switch (page) {
            case PAGE_GANK_ANDROID:
                if (null == mGankAndroidFragment) {
                    mGankAndroidFragment = AndGankFrag.newInstance();
                    ts.add(R.id.main_container, mGankAndroidFragment, AndGankFrag.TAG);
                } else {
                    ts.show(mGankAndroidFragment);
                }
                break;
            case PAGE_IMPORT_NEW:
                if (null == mImportNewFragment) {
                    mImportNewFragment = ImportNewFragment.newInstance();
                    ts.add(R.id.main_container, mImportNewFragment, AndGankFrag.TAG);
                } else {
                    ts.show(mImportNewFragment);
                }
                break;
            case PAGE_GANK_DAILY:
                if (null == mGankDailyFragment) {
                    mGankDailyFragment = GankDailyFragment.newInstance();
                    ts.add(R.id.main_container, mGankDailyFragment, AndGankFrag.TAG);
                } else {
                    ts.show(mGankDailyFragment);
                }
                break;
            case PAGE_GANK_MEIZI:
                if (null == mGankMeiziFragment) {
                    mGankMeiziFragment = MeiziFragment.newInstance();
                    ts.add(R.id.main_container, mGankMeiziFragment, AndGankFrag.TAG);
                } else {
                    ts.show(mGankMeiziFragment);
                }
                break;

        }
        ts.commit();
    }

    private void hideAll() {
        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();
        if (null != mGankAndroidFragment) {
            ts.hide(mGankAndroidFragment);
        }
        if (null != mImportNewFragment) {
            ts.hide(mImportNewFragment);
        }
        if (null != mGankDailyFragment) {
            ts.hide(mGankDailyFragment);
        }
        if (null != mGankMeiziFragment) {
            ts.hide(mGankMeiziFragment);
        }
        ts.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(TAG, "onCreateOptionsMenu() called with: " + "menu = [" + menu + "]");
        getMenuInflater().inflate(R.menu.main, menu);
        setupSearchItem(menu);
        return true;
    }

    //// TODO: 15/11/24  https://www.youtube.com/watch?v=9OWmnYPX1uc
    private void setupSearchItem(Menu menu) {
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
//        searchView.setOnQueryTextFocusChangeListener();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Snackbar.make(mFab, query, Snackbar.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int pageDst = mCurrPage;

        int id = item.getItemId();

        if (id == R.id.gank) {
            pageDst = PAGE_GANK_ANDROID;
        } else if (id == R.id.import_new) {
            pageDst = PAGE_IMPORT_NEW;
        } else if (id == R.id.gank_daily) {
            pageDst = PAGE_GANK_DAILY;
        } else if (id == R.id.gank_meizi) {
            pageDst = PAGE_GANK_MEIZI;
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.setting) {

        }else if(id==R.id.daynight) {

//            if (getDelegate().applyDayNight()) {
//                getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }else{
//                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            }
//            recreate();
        }

        changePage(pageDst);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
