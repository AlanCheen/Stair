package yfy.github.stair.utils;

import android.util.Log;

/**
 * Created by 程序亦非猿 on 16/3/15.
 */
public class ZLog implements Logger{

    @Override
    public void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg,tr);
    }

}
