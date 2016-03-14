package yfy.github.stair.utils;

import android.os.Build;

/**
 * Created by 程序亦非猿 on 16/2/22.
 */
public class VersionUtil {

    public static boolean isAndroid6() {
        return Build.VERSION.SDK_INT>=21;
    }
}
