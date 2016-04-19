package yfy.github.stair.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yfy.github.stair.R;
import yfy.github.stair.api.Api;
import yfy.github.stair.data.GankDaily;
import yfy.github.stair.data.GankEntity;
import yfy.github.stair.utils.TransitionListenerAdapter;
import yfy.github.stair.utils.VersionUtil;

public class GankDetailActivity extends ToolbarActivity {

    private static final String TAG = "GankDetailActivity";
    public static final String KEY_DETAIL = "key_gank_detail";
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.iv_meizi)
    ImageView mIvMeizi;
    private GankEntity mEntity;
//    KenBurnsView mIvMeizi;

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


        mEntity = (GankEntity) getIntent().getSerializableExtra(KEY_DETAIL);
        updateTitle(mEntity.desc);

        requestData();

        if (VersionUtil.isAndroid6()) {
            mFab.setScaleX(0);
            mFab.setScaleY(0);
            getWindow().getEnterTransition().addListener(new TransitionListenerAdapter() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    getWindow().getEnterTransition().removeListener(this);
                    mFab.animate().scaleY(1).scaleX(1).setDuration(300).start();
                }
            });
        }

    }


    @OnClick({R.id.iv_meizi})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_meizi:

                Intent i = PhotoActivity.createIntent(mContext, mEntity.url);
                startActivity(i);
                break;

        }
    }


    private void requestData() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = format.parse(mEntity.publishedAt);
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            Observable<GankDaily> daily = Api.getIns().getDaily(year, month, day);
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
                            Glide.with(mContext).load(mEntity.url).centerCrop().into(mIvMeizi);
                        }
                    });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
