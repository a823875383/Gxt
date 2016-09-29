package gxt.jsqix.com.mycommon.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by dongqing on 16/9/12.
 */
public abstract class BaseFragment extends Fragment {
    protected BaseCompat mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = (BaseCompat) getActivity();
        getArgument();
        initView();
    }

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 获取传递数据
     */
    protected abstract void getArgument();
}
