package yfy.github.stair.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import yfy.github.stair.BuildConfig;
import yfy.github.stair.data.GAndroid;
import yfy.github.stair.data.GankDaily;

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

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30_000, TimeUnit.MILLISECONDS)
                .readTimeout(30_000, TimeUnit.SECONDS)
                .writeTimeout(30_000, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        final OkHttpClient mOkHttpClient = builder.build();

        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")   //("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankConfig.URL_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                .client(mOkHttpClient)
                .build();

//        RxJavaPlugins.getInstance().registerObservableExecutionHook(new RxJavaObservableExecutionHook() {
//            @Override
//            public <T> Observable.OnSubscribe<T> onSubscribeStart(Observable<? extends T> observableInstance, Observable.OnSubscribe<T> onSubscribe) {
//                //很遗憾 不能完全这么做
//                observableInstance.subscribeOn(AndroidSchedulers.mainThread());
//                return super.onSubscribeStart(observableInstance, onSubscribe);
//            }
//        });

        mGankApi = retrofit.create(GankApi.class);

    }

    public  Observable<GAndroid> getAndroid(int page) {
        return mGankApi.getAndroid(page);
    }

    public Observable<GAndroid> getMeizi(int page){
        return mGankApi.getMeizi(page);
    }

   public Observable<GankDaily> getDaily(int year,int month,int day){
       return mGankApi.getDaily(year,month,day);
   }

}
