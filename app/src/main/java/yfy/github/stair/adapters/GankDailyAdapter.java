package yfy.github.stair.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yfy.github.stair.R;
import yfy.github.stair.data.GankDaily;

/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/11/23
 */
public class GankDailyAdapter extends RecyclerView.Adapter<GankDailyAdapter.GankDailyViewHolder> {

    private List<GankDaily> mDatas;
    private Context mContext;

    public GankDailyAdapter(Context context, List<GankDaily> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public GankDailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_daily, parent, false);
        return new GankDailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankDailyViewHolder holder, int position) {

        GankDaily entity = mDatas.get(position);

        holder.itemView.setOnClickListener(v -> {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemCliek(entity);
            }
        });

        Glide.with(mContext).load(entity.results.福利.get(0).url).into(holder.ivMeizi);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class GankDailyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.content)
        TextView tvContent;
        @Bind(R.id.iv_meizi)
        ImageView ivMeizi;
        public GankDailyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private onItemClickListener mOnItemClickListener;
    public interface onItemClickListener {
        void onItemCliek(GankDaily entity);
    }

}
