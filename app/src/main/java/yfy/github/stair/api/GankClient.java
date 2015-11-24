package yfy.github.stair.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import yfy.github.stair.BuildConfig;

/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/10/8
 */
public class GankClient {

    private volatile static GankClient sGankClient;

    public static GankClient getIns() {
        if (sGankClient == null) {
            synchronized (GankClient.class) {
                if (null == sGankClient) {
                    sGankClient = new GankClient();
                }
            }
        }
        return sGankClient;
    }

    private final GankApi mGankApi;

    private GankClient() {

        OkHttpClient mOkHttpClient = new OkHttpClient();
//        mOkHttpClient.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//
//                return null;
//            }
//        });

        //Notice log必须加在最后
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            mOkHttpClient.interceptors().add(logging);
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")   //("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        Retrofit gank = new Retrofit.Builder().baseUrl(GankConfig.URL_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mGankApi = gank.create(GankApi.class);
    }

    public GankApi getGankApi() {
        return mGankApi;
    }

}
