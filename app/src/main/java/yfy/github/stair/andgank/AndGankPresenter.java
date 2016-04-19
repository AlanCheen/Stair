package yfy.github.stair.andgank;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import yfy.github.stair.api.Api;
import yfy.github.stair.data.GAndroid;

/**
 * Created by 程     on 16/4/18.
 */
public class AndGankPresenter implements AndGankContract.Presenter {


    private final AndGankContract.View mView;

    public AndGankPresenter(AndGankContract.View view) {
        mView = view;
//        mView.attach(this);
    }

    @Override
    public void requestData(int page) {

        mView.showLoading(true);
        Api.getIns().getAndroid(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GAndroid>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("ERROR!!:" + e.getMessage());
                        mView.showLoading(false);
                    }

                    @Override
                    public void onNext(GAndroid gAndroid) {
                        mView.showData(gAndroid.results);
                        mView.showLoading(false);
                    }
                });
    }
}
