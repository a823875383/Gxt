package com.jsqix.gxt.app.fragment;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.AddressManage;
import com.jsqix.gxt.app.activity.BankcardAdded;
import com.jsqix.gxt.app.activity.ChangePassword;
import com.jsqix.gxt.app.activity.ChangePhoneFirst;
import com.jsqix.gxt.app.activity.RechargeFirst;
import com.jsqix.gxt.app.activity.WithdrawActivity;
import com.jsqix.utils.DensityUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 采购商个人中心
 */

@ContentView(R.layout.fragment_purchaser)
public class PurchaserFragment extends BaseFragment {
    @ViewInject(R.id.layout_user)
    private LinearLayout users;
    @ViewInject(R.id.iv_set)
    private ImageView imgSet;


    public PurchaserFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initView() {
        imgSet.setVisibility(View.GONE);

        //title margin透明通知栏高度
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) users.getLayoutParams();
        lp.setMargins(0, StatusBarCompat.getStatusBarHeight(mContext) + DensityUtil.dip2px(mContext, 5), 0, 0);
        users.setLayoutParams(lp);
    }

    @Override
    protected void getArgument() {

    }


    @Event(R.id.bt_recharge)
    private void rechargeClick(View v) {
        startActivity(new Intent(mContext, RechargeFirst.class));
    }

    @Event(R.id.bt_withdraw)
    private void withDrawClick(View v) {
        startActivity(new Intent(mContext, WithdrawActivity.class));
    }

    @Event(R.id.lin_bank)
    private void addedClick(View v) {
        startActivity(new Intent(mContext, BankcardAdded.class));
    }

    @Event(R.id.lin_address)
    private void addressClick(View v) {
        startActivity(new Intent(mContext, AddressManage.class));
    }

    @Event(R.id.tv_change_pass)
    private void changePass(View v) {
        startActivity(new Intent(mContext, ChangePassword.class));
    }

    @Event(R.id.tv_change_phone)
    private void changePhone(View v) {
        startActivity(new Intent(mContext, ChangePhoneFirst.class));
    }
}
