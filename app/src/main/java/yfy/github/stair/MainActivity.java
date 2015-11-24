package yfy.github.stair;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;
import yfy.github.stair.data.GankAndroidFragment;
import yfy.github.stair.data.GankDailyFragment;
import yfy.github.stair.data.ImportNewFragment;
import yfy.github.stair.data.MeiziFragment;
import yfy.github.stair.ui.BaseActivity;
import yfy.github.stair.ui.BaseFragment;


/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/10/8
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected static final String TAG = "MainActivity";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.nav_view)
    NavigationView mNavView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private BaseFragment mGankAndroidFragment;
    private BaseFragment mImportNewFragment;
    private BaseFragment mGankDailyFragment;
    private BaseFragment mGankMeiziFragment;
    private int mCurrPage;

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        if (null == savedInstanceState) {
            changePage(PAGE_GANK_ANDROID);
        }
    }

    @IntDef({PAGE_GANK_ANDROID,PAGE_IMPORT_NEW,PAGE_GANK_DAILY,PAGE_GANK_MEIZI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Page{}

    public static final int PAGE_GANK_ANDROID = 1;
    public static final int PAGE_IMPORT_NEW = 2;
    public static final int PAGE_GANK_DAILY = 3;
    public static final int PAGE_GANK_MEIZI = 4;

    private void changePage(@Page int page) {
        if (mCurrPage == page) {
            return;
        }
        mCurrPage = page;
        hideAll();

        FragmentTransaction ts = getSupportFragmentManager().beginTransaction();

        switch (page) {
            case PAGE_GANK_ANDROID:
                if (null==mGankAndroidFragment){
                    mGankAndroidFragment = GankAndroidFragment.newInstance();
                    ts.add(R.id.main_container, mGankAndroidFragment, GankAndroidFragment.TAG);
                }else {
                    ts.show(mGankAndroidFragment);
                }
                break;
            case PAGE_IMPORT_NEW:
                if (null==mImportNewFragment){
                    mImportNewFragment = ImportNewFragment.newInstance();
                    ts.add(R.id.main_container, mImportNewFragment, GankAndroidFragment.TAG);
                }else {
                    ts.show(mImportNewFragment);
                }
                break;
            case PAGE_GANK_DAILY:
                if (null==mGankDailyFragment){
                    mGankDailyFragment = GankDailyFragment.newInstance();
                    ts.add(R.id.main_container, mGankDailyFragment, GankAndroidFragment.TAG);
                }else {
                    ts.show(mGankDailyFragment);
                }
                break;
            case PAGE_GANK_MEIZI:
                if (null==mGankMeiziFragment){
                    mGankMeiziFragment = MeiziFragment.newInstance();
                    ts.add(R.id.main_container, mGankMeiziFragment, GankAndroidFragment.TAG);
                }else {
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
        // Inflate the menu; this adds items to the action bar if it is present.
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
                Snackbar.make(mFab, query, Snackbar.LENGTH_SHORT).show();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int pageDst = PAGE_GANK_ANDROID;

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

        } else if (id == R.id.nav_send) {

        }
        changePage(pageDst);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
