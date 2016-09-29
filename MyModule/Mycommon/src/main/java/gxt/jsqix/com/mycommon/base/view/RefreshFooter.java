package gxt.jsqix.com.mycommon.base.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;

import gxt.jsqix.com.mycommon.R;

/**
 * Created by dongqing on 16/9/26.
 */

public class RefreshFooter extends LoadingLayoutBase {
    private FrameLayout mFooterView;
    private ProgressBar mFooterProgressBar;
    private ImageView mFooterImageView;
    private TextView mFooterTextView;


    public RefreshFooter(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.refresh_footer, this);
        mFooterView = (FrameLayout) findViewById(R.id.pull_to_refresh_footer);
        mFooterImageView = (ImageView) findViewById(R.id.pull_to_load_image);
        mFooterTextView = (TextView) findViewById(R.id.pull_to_load_text);
        mFooterProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_progress);
        LayoutParams lp = (LayoutParams) mFooterView.getLayoutParams();
        lp.gravity = Gravity.TOP;
        reset();
    }

    @Override
    public int getContentSize() {
        return mFooterView.getHeight();
    }

    @Override
    public void pullToRefresh() {
        mFooterTextView.setText("上拉加载更多");
    }

    @Override
    public void releaseToRefresh() {
        mFooterTextView.setText("松开加载更多");
    }

    @Override
    public void onPull(float scaleOfLayout) {

    }

    @Override
    public void refreshing() {
        mFooterTextView.setText("正在加载...");
    }

    @Override
    public void reset() {
        mFooterTextView.setText("上拉加载更多");

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
