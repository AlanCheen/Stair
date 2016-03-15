package yfy.github.stair.utils;

/**
 * Created by 程序亦非猿 on 16/3/15.
 */
public interface Logger {
    void v(String tag, String msg);
    void d(String tag, String msg);
    void i(String tag, String msg);
    void e(String tag, String msg,Throwable tr);
}
