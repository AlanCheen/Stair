package yfy.github.stair.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoViewAttacher;
import yfy.github.stair.R;

public class PhotoActivity extends ToolbarActivity {

    private static final String KEY_URL = "URL";
    @Bind(R.id.iv_photo)
    ImageView mPhoto;

    PhotoViewAttacher mAttacher;

    public static Intent createIntent(Context cxt, String url) {
        Intent intent = new Intent(cxt, PhotoActivity.class);
        intent.putExtra(KEY_URL, url);

        return intent;
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(KEY_URL);
        Glide.with(this).load(url).into(mPhoto);
        mAttacher = new PhotoViewAttacher(mPhoto);

    }

}
