package yfy.github.stair.ui;

import android.os.Bundle;

import yfy.github.stair.MainActivity;
import yfy.github.stair.base.BaseActivity;

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
