package com.jsqix.gxt.app.fragment;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.activity.AddressManage;
import com.jsqix.gxt.app.activity.BankcardAdded;
import com.jsqix.gxt.app.activity.ChangePassword;
import com.jsqix.gxt.app.activity.ChangePhoneFirst;
import com.jsqix.gxt.app.activity.RechargeFirst;
import com.jsqix.gxt.app.activity.WithdrawActivity;
import com.jsqix.gxt.app.obj.BalanceResult;
import com.jsqix.gxt.app.obj.CountResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.DensityUtil;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseFragment;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.util.CommUtils;
import gxt.jsqix.com.mycommon.base.util.StatusBarCompat;

/**
 * 采购商个人中心
 */

@ContentView(R.layout.fragment_purchaser)
public class PurchaserFragment extends BaseFragment implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.layout_user)
    private LinearLayout users;
    @ViewInject(R.id.iv_set)
    private ImageView imgSet;
    @ViewInject(R.id.tv_money)
    private TextView balanceAvailable;
    @ViewInject(R.id.tv_address)
    private TextView addressCount;
    @ViewInject(R.id.tv_bank)
    private TextView bankCount;

    final static int BALANCE_QUERY = 0x0001, ADDRESS_COUNT = 0x0010, BANK_COUNT = 0x0011;

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

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            queryBank();
            queryAddress();
            queryBalance();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            queryBank();
            queryAddress();
            queryBalance();
        }
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

    /**
     * 查询用户余额
     */
    private void queryBalance() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {
                mContext.loadingUtils.show();
            }
        };
        get.setResultCode(BALANCE_QUERY);
        get.execute(RequestIP.GET_USER_BALANCE);
    }

    /**
     * 查询用户收货地址数量
     */
    private void queryAddress() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(ADDRESS_COUNT);
        get.execute(RequestIP.ADDRESS_COUNT);
    }

    /**
     * 查询用户收货地址数量
     */
    private void queryBank() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", mContext.aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(mContext, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(BANK_COUNT);
        get.execute(RequestIP.BLANK_COUNT);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case BALANCE_QUERY:
                balanceResult(result);
                break;
            default:
                countResult(resultCode, result);
                break;
        }
        mContext.loadingUtils.dismiss();
    }

    private void countResult(int resultCode, String result) {
        CountResult countResult = new Gson().fromJson(result, CountResult.class);
        if (countResult != null) {
            if (countResult.getCode().equals("000")) {
                switch (resultCode) {
                    case ADDRESS_COUNT:
                        addressCount.setText(getString(R.string.my_address).replace("x", countResult.getObj() + ""));
                        break;
                    case BANK_COUNT:
                        bankCount.setText(getString(R.string.blank).replace("x", countResult.getObj() + ""));
                        break;
                }
            } else {
                Utils.makeToast(mContext, countResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }

    }

    private void balanceResult(String result) {
        BalanceResult balanceResult = new Gson().fromJson(result, BalanceResult.class);
        if (balanceResult != null) {
            if (balanceResult.getCode().equals("000")) {
                balanceAvailable.setText(CommUtils.toFormat(balanceResult.getObj() / 100.0));
            } else {
                Utils.makeToast(mContext, balanceResult.getMsg());
            }
        } else {
            Utils.makeToast(mContext, getString(R.string.network_timeout));
        }
    }
}
