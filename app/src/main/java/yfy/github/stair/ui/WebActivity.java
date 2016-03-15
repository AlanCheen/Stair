package yfy.github.stair.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;
import yfy.github.stair.R;
import yfy.github.stair.views.SmartWebView;

public class WebActivity extends ToolbarActivity {

    public static final String KEY_URL = "key_url";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webview)
    SmartWebView mWebview;

    @Bind(R.id.progress)
    ProgressBar mProgressBar;
    private String mUrl;

    public static Intent creatIntent(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mWebview.setCallback(new SmartWebView.Callback() {
            @Override
            public void onProgressChanged(int newProgress) {

                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(newProgress);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(String title) {
                setTitle(title);
            }

            @Override
            public void onPageFinished(String url) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

        mUrl = getIntent().getStringExtra(KEY_URL);
        mWebview.loadUrl(mUrl);

    }


    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebview.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.open:
                break;
            case R.id.share:
                break;
            case R.id.collect:
                break;
            case R.id.copy_link:
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setPrimaryClip(ClipData.newPlainText("URL",mUrl));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
