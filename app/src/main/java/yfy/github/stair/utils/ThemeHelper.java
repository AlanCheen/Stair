package yfy.github.stair.utils;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 程序亦非猿 on 16/3/15.
 */
public class ThemeHelper {


    public static void fullScreen(Activity act) {
        act.requestWindowFeature(Window.FEATURE_NO_TITLE);
        act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
