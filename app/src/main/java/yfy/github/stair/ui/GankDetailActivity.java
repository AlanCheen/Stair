package yfy.github.stair.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yfy.github.stair.R;
import yfy.github.stair.api.GankClient;
import yfy.github.stair.data.GankDaily;
import yfy.github.stair.data.GankEntity;

public class GankDetailActivity extends ToolbarActivity {

    private static final String TAG = "GankDetailActivity";
    public static final String KEY_DETAIL = "key_gank_detail";
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.iv_meizi)
    KenBurnsView mIvMeizi;

    public static Intent creatIntent(Context context, GankEntity gank) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(KEY_DETAIL, gank);
        return intent;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        requestData();
    }


    private void requestData() {
        GankEntity entity = (GankEntity) getIntent().getSerializableExtra(KEY_DETAIL);

        updateTitle(entity.desc);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = format.parse(entity.publishedAt);
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            Observable<GankDaily> daily = GankClient.getIns().getGankApi().getDaily(year, month, day);
            Log.d(TAG, "requestData: daily" + daily);
            daily
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GankDaily>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted() called with: " + "");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Snackbar.make(mFab, "oh,no ", Snackbar.LENGTH_LONG).setAction("Try Again", (v) -> {
                                requestData();
                            }).show();
                        }

                        @Override
                        public void onNext(GankDaily gankDaily) {
                            Log.d(TAG, "onNext() called with: " + "gankDaily = [" + gankDaily + "]");
                            Glide.with(mContext).load(entity.url).centerCrop().into(mIvMeizi);
                        }
                    });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mIvMeizi.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mIvMeizi.pause();
    }

}
