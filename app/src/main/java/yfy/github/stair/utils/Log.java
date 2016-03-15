package yfy.github.stair.utils;

import yfy.github.stair.BuildConfig;

/**
 * Created by 程序亦非猿 on 16/3/15.
 */
public class Log {

    private static ZLog sZLog = new ZLog();

    private static boolean logable() {
        return BuildConfig.DEBUG;
    }

    public static void v(String tag, String msg) {
        if (logable()) {
            sZLog.v(tag,msg);
        }
    }

    public static void d(String tag, String msg) {
        if (logable()) {
            sZLog.d(tag,msg);
        }
    }

    public static void i(String tag, String msg) {
        if (logable()) {
            sZLog.i(tag,msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (logable()) {
            sZLog.e(tag,msg,tr);
        }
    }


}
