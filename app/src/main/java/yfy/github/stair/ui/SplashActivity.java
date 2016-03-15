package yfy.github.stair.ui;

import android.os.Bundle;

import yfy.github.stair.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int provideLayoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        startActivity(MainActivity.createIntent(this));
        finish();
    }
}
