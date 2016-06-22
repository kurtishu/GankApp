package com.github.kurtishu.gank.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.config.GankConst;
import com.github.kurtishu.gank.ui.activity.HomeActivity;
import com.github.kurtishu.gank.util.PreferencesUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kurtishu on 6/22/16.
 */
public class NavigatorPagerAdapter extends PagerAdapter {

    private LayoutInflater mInflater;
    private String[] imageNames = new String[]{"image01", "image02", "image03", "image04", "image05" };
    private Activity mContext;

    public NavigatorPagerAdapter(Activity context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageNames.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View contentView = mInflater.inflate(R.layout.item_navigator, container, false);
        ImageView imageView = (ImageView)contentView.findViewById(R.id.introduce_image);
        Button btnStart = (Button) contentView.findViewById(R.id.button_get_start);

        setImages(imageView, position);
        if ((position + 1) == getCount()) {
            btnStart.setVisibility(View.VISIBLE);
        } else {
            btnStart.setVisibility(View.GONE);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtil.setBooleanValue(GankConst.KEY_INIT_INSTALL, false);
                mContext.startActivity(new Intent(mContext, HomeActivity.class));
                mContext.finish();
            }
        });

        container.addView(contentView);
        return contentView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void setImages(ImageView imageView, int position) {
        String selectedImage = "screen/" + imageNames[position] + ".png";
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open(selectedImage);
            Glide.with(mContext).load(input2byte(inputStream)).into(imageView);
        } catch (IOException e) {
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

    }

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
}
