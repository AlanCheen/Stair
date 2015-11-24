package yfy.github.stair.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import butterknife.Bind;
import yfy.github.stair.R;

public class WebActivity extends ToolbarActivity {

    public static final String KEY_URL = "key_url";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webview)
    WebView mWebview;

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
        String url = getIntent().getStringExtra(KEY_URL);
        mWebview.loadUrl(url);
    }
}
