package gxt.jsqix.com.mycommon.base.view;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;

import gxt.jsqix.com.mycommon.R;

/**
 * Created by dongqing on 16/9/26.
 */

public class RefreshHeader extends LoadingLayoutBase {
    private FrameLayout mHeaderView;
    private ProgressBar mHeaderProgressBar;
    private ImageView mHeaderImageView;
    private TextView mHeaderTextView;
    private RotateAnimation mFlipAnimation;
    private boolean isUp = false;

    public RefreshHeader(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.refresh_header, this);
        mHeaderView = (FrameLayout) findViewById(R.id.pull_to_refresh_header);
        mHeaderImageView = (ImageView) findViewById(R.id.pull_to_refresh_image);
        mHeaderTextView = (TextView) findViewById(R.id.pull_to_refresh_text);
        mHeaderProgressBar = (ProgressBar) findViewById(R.id.pull_to_refresh_progress);
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
        LayoutParams lp = (LayoutParams) mHeaderView.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        reset();
    }

    @Override
    public int getContentSize() {
        return mHeaderView.getHeight();
    }

    @Override
    public void pullToRefresh() {
        if (isUp) {
            mHeaderImageView.setImageResource(R.mipmap.arrow);
            isUp = false;
        }
        mHeaderTextView.setText("下拉可以刷新");
    }

    @Override
    public void releaseToRefresh() {
        isUp = true;
        mHeaderImageView.clearAnimation();
        mHeaderImageView.startAnimation(mFlipAnimation);
        mHeaderTextView.setText("松开开始刷新");
    }

    @Override
    public void onPull(float scaleOfLayout) {

    }

    @Override
    public void refreshing() {
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.clearAnimation();
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText("正在刷新...");
    }

    @Override
    public void reset() {
        isUp = false;
        mHeaderImageView.clearAnimation();
        mHeaderImageView.setVisibility(View.VISIBLE);
        mHeaderProgressBar.setVisibility(View.GONE);
        mHeaderTextView.setText("下拉可以刷新");
    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {

    }
}
