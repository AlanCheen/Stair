package yfy.github.stair;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Process;

import com.umeng.analytics.MobclickAgent;

import java.util.Stack;

/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/11/10
 */
public class Stair extends Application implements Thread.UncaughtExceptionHandler,Application.ActivityLifecycleCallbacks{


    private static final String TAG = "Stair";

    private static Stair sIns;
    private Stack<Activity> mActivityStack = new Stack<>();


    @Override
    public void onCreate() {
        super.onCreate();
        sIns = this;
        Thread.currentThread().setUncaughtExceptionHandler(this);
        MobclickAgent.openActivityDurationTrack(false);
    }


    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    public static Stair getIns() {
        return sIns;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        android.os.Process.killProcess(Process.myPid());
        MobclickAgent.onKillProcess(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityStack.push(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityStack.remove(activity);
    }
}
