package yfy.github.stair.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import yfy.github.stair.R;
import yfy.github.stair.data.GankEntity;

/**
 * Stair github:  https://github.com/AlanCheen/Stair
 * Created by 程序亦非猿 (http://weibo.com/alancheeen)
 * on 15/11/23
 */
public class GankMeiziAdapter extends RecyclerView.Adapter<GankMeiziAdapter.GankViewHolder> {

    private List<GankEntity> mDatas;
    private Context mContext;

    public GankMeiziAdapter(Context context, List<GankEntity> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_meizi, parent, false);
        return new GankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankViewHolder holder, int position) {

        GankEntity entity = mDatas.get(position);

        Glide.with(mContext)
                .load(entity.url)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .override(300,400)
//                .crossFade(R.anim.fade_in)
                .centerCrop()
                .into(holder.ivMeizi);

        holder.itemView.setOnClickListener(v -> {
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemCliek(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class GankViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_meizi)
        ImageView ivMeizi;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener {
        void onItemCliek(GankEntity entity);
    }

}
