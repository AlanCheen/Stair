package yfy.github.stair.andgank;

import java.util.List;

import yfy.github.stair.base.BasePresenter;
import yfy.github.stair.base.BaseView;
import yfy.github.stair.data.GankEntity;

/**
 * Created by 程     on 16/4/18.
 */
public interface AndGankContract {

    interface View extends BaseView<Presenter> {
        void showData(List<GankEntity> entities);
        void showError(String msg);
        void showLoading(boolean isLoading);
    }

    interface Presenter extends BasePresenter {
        void requestData(int page);
    }
}
