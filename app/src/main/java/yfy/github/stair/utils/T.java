package yfy.github.stair.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 程序亦非猿 on 16/3/15.
 */
public class T {

    private static Toast sToast ;

    public static void show(Context cxt,String content) {
        if (null != sToast) {
            sToast.cancel();
        }
        sToast = Toast.makeText(cxt, content, Toast.LENGTH_SHORT);
        sToast.show();
    }
}
