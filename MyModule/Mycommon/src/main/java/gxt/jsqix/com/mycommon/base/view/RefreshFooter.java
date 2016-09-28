package gxt.jsqix.com.mycommon.base.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;

import gxt.jsqix.com.mycommon.R;

/**
 * Created by dongqing on 16/9/26.
 */

public class RefreshFooter extends LoadingLayoutBase {
    private RelativeLayout mFooterView;
    private ProgressBar mFooterProgressBar;
    private ImageView mFooterImageView;
    private TextView mFooterTextView;


    public RefreshFooter(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.refresh_footer, this);
        mFooterView = (RelativeLayout) findViewById(R.id.pull_to_refresh_footer);
        mFooterImageView = (ImageView) findViewById(R.id.pull_to_load_image);
        mFooterTextView = (TextView) findViewById(R.id.pull_to_load_text);
        mFooterProgressBar = (ProgressBar) findViewById(R.id.pull_to_load_progress);

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
