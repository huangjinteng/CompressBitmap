package com.webapps.bitmap;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;


/**
 * 定义一个全局的盒子,里面定义的方法和变量都是全局可用的
 */
public class BaseApplication extends Application {

    private static Context mContext;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Looper mLooper;
    private static Handler mHandler;
    private static int statusHeight;
    private static int screenWidth;
    private static int screenHeight;
    private static String token;
    private static Map<String,String> headers;
    private static String session;


    public static String getSession() {
        return session;
    }

    public static void setSession(String session) {
        BaseApplication.session = session;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getLooper() {
        return mLooper;
    }

    public static int getStatusHeight() {
        return statusHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseApplication.token = token;
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
    }

    public static Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public void onCreate() { // 程序入口
        // 初始化一些常用属性

        // 上下文路径
        mContext = getApplicationContext();

        // 主线程
        mMainThread = Thread.currentThread();

        // 主线程ID
        mMainThreadId = android.os.Process.myTid();

        // 主线程对象
        mLooper = getMainLooper();

        // 定义一个handler
        mHandler = new Handler();


        statusHeight = getStatusBarHeight();

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;




//        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.


//        DbCookieStore instance = DbCookieStore.INSTANCE;
//        List cookies = instance.getCookies();
//        for (int i = 0; i < cookies.size(); i++) {
//            LogUtils.sf("XUtils学信网:" + cookies.get(i));
//            if ((cookies.get(i)+"").contains("JSESSIONID")) {
//                session = cookies.get(i).toString().replace("JSESSIONID=", "");
//                break;
//            }
//        }

        super.onCreate();

    }

    // 获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



}
