package com.jsqix.gxt.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jsqix.gxt.app.R;
import com.jsqix.gxt.app.obj.BalanceResult;
import com.jsqix.gxt.app.obj.BankListResult;
import com.jsqix.gxt.app.utils.Constant;
import com.jsqix.utils.StringUtils;
import com.jsqix.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import gxt.jsqix.com.mycommon.base.BaseToolActivity;
import gxt.jsqix.com.mycommon.base.api.HttpGet;
import gxt.jsqix.com.mycommon.base.api.RequestIP;
import gxt.jsqix.com.mycommon.base.bean.BaseBean;
import gxt.jsqix.com.mycommon.base.util.CommUtils;

/**
 * 提现
 */
@ContentView(R.layout.activity_withdraw)
public class WithdrawActivity extends BaseToolActivity implements HttpGet.InterfaceHttpGet {
    @ViewInject(R.id.rel_choose)
    private RelativeLayout rel_choose;
    @ViewInject(R.id.iv_bank_icon)
    private ImageView bankIcon;
    @ViewInject(R.id.tv_bank_name)
    private TextView bankName;
    @ViewInject(R.id.tv_bank_number)
    private TextView bankNum;
    @ViewInject(R.id.lin_add)
    private LinearLayout addBank;
    @ViewInject(R.id.et_money)
    private EditText inputMoney;
    @ViewInject(R.id.tv_available)
    private TextView balanceAvailable;

    private int bankId;
    private double avaiableMoney;
    final static int BALANCE_QUERY = 0x0001, BANK_LIST = 0x0010, WITHDRAW = 0x0011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitle() {
        mTitle.setText(getString(R.string.balance_withdraw));
    }

    @Override
    protected void initView() {
        inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankList();
        queryBalance();
    }

    @Event(R.id.rel_choose)
    private void chooseClick(View v) {
        Intent intent = new Intent(this, BankcradChoose.class);
        intent.putExtra(Constant.ID, bankId);
        startActivityForResult(intent, 100);

    }

    @Event(R.id.lin_add)
    private void addClick(View v) {
        startActivity(new Intent(this, BankcardAdd.class));

    }

    @Event(R.id.bt_done)
    private void withdrawClick(View v) {
        if (!StringUtils.isEmpty(CommUtils.textToString(inputMoney))) {
            withdraw(CommUtils.textToString(inputMoney));
        } else {
            Utils.makeToast(this, "请输入提现金额");
        }
    }

    /**
     * 银行卡列表
     */
    private void getBankList() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(BANK_LIST);
        get.execute(RequestIP.BACK_LISTS);
    }

    /**
     * 查询用户余额
     */
    private void queryBalance() {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(BALANCE_QUERY);
        get.execute(RequestIP.GET_USER_BALANCE);
    }

    /**
     * 提现
     */
    private void withdraw(String amount) {
        Map<String, Object> paras = new HashMap<>();
        paras.put("userId", aCache.getAsString(Constant.U_ID));
        paras.put("timeStamp", System.currentTimeMillis());
        paras.put("userBankId", bankId);
        paras.put("total", amount);
        HttpGet get = new HttpGet(this, paras, this) {
            @Override
            public void onPreExecute() {

            }
        };
        get.setResultCode(WITHDRAW);
        get.execute(RequestIP.PROC_PRESENT_APPLYS);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        switch (resultCode) {
            case BANK_LIST:
                dataResult(result);
                break;
            case BALANCE_QUERY:
                balanceResult(result);
                break;
            case WITHDRAW:
                withdrawResult(result);
                break;
        }
    }

    private void withdrawResult(String result) {
        BaseBean basebean = new Gson().fromJson(result, BaseBean.class);
        if (basebean != null) {
            Utils.makeToast(this, basebean.getMsg());
            if (basebean.getCode().equals("000")) {
                queryBalance();
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void balanceResult(String result) {
        BalanceResult balanceResult = new Gson().fromJson(result, BalanceResult.class);
        if (balanceResult != null) {
            if (balanceResult.getCode().equals("000")) {
                avaiableMoney = balanceResult.getObj() / 100.0;
                balanceAvailable.setText(getString(R.string.balance_available).replace("x", CommUtils.toFormat(avaiableMoney)));
            } else {
                Utils.makeToast(this, balanceResult.getMsg());
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    private void dataResult(String result) {
        BankListResult listResult = new Gson().fromJson(result, BankListResult.class);
        if (listResult != null) {
            if (listResult.getCode().equals("000")) {
                if (listResult.getObj().size() > 0) {
                    rel_choose.setVisibility(View.VISIBLE);
                    addBank.setVisibility(View.GONE);
                    BankListResult.ObjBean item = listResult.getObj().get(0);
                    if (item.getBank_no() == 102801) {
                        bankName.setText("中国建设银行");
                        bankIcon.setImageResource(R.mipmap.bank_ccb);

                    } else if (item.getBank_no() == 102802) {
                        bankName.setText("中国工商银行");
                        bankIcon.setImageResource(R.mipmap.bank_icbc);

                    } else if (item.getBank_no() == 102803) {
                        bankName.setText("中国农业银行");
                        bankIcon.setImageResource(R.mipmap.bank_abc);

                    } else if (item.getBank_no() == 102804) {
                        bankName.setText("中国交通银行");
                        bankIcon.setImageResource(R.mipmap.bank_comm);

                    } else if (item.getBank_no() == 102805) {
                        bankName.setText("中国银行");
                        bankIcon.setImageResource(R.mipmap.bank_boc);

                    } else if (item.getBank_no() == 102806) {
                        bankName.setText("中国招商银行");
                        bankIcon.setImageResource(R.mipmap.bank_cmb);

                    } else if (item.getBank_no() == 102807) {
                        bankName.setText("中国民生银行");
                        bankIcon.setImageResource(R.mipmap.bank_cmbc);

                    } else if (item.getBank_no() == 102808) {
                        bankName.setText("浦东发展银行");
                        bankIcon.setImageResource(R.mipmap.bank_spdb);

                    } else if (item.getBank_no() == 102809) {
                        bankName.setText("邮政储蓄银行");
                        bankIcon.setImageResource(R.mipmap.bank_psbc);

                    } else if (item.getBank_no() == 102810) {
                        bankName.setText("中国中信银行");
                        bankIcon.setImageResource(R.mipmap.bank_citic);

                    } else if (item.getBank_no() == 102811) {
                        bankName.setText("中国光大银行");
                        bankIcon.setImageResource(R.mipmap.bank_ceb);

                    }
                    bankNum.setText("尾号" + item.getBank_account());
                    bankId = item.getId();
                } else {
                    rel_choose.setVisibility(View.GONE);
                    addBank.setVisibility(View.VISIBLE);
                }
            } else {
                Utils.makeToast(this, listResult.getMsg());
                rel_choose.setVisibility(View.GONE);
                addBank.setVisibility(View.VISIBLE);
            }
        } else {
            Utils.makeToast(this, getString(R.string.network_timeout));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data.getExtras() != null) {
                BankListResult.ObjBean item = (BankListResult.ObjBean) data.getSerializableExtra(Constant.DATA);
                if (item.getBank_no() == 102801) {
                    bankName.setText("中国建设银行");
                    bankIcon.setImageResource(R.mipmap.bank_ccb);

                } else if (item.getBank_no() == 102802) {
                    bankName.setText("中国工商银行");
                    bankIcon.setImageResource(R.mipmap.bank_icbc);

                } else if (item.getBank_no() == 102803) {
                    bankName.setText("中国农业银行");
                    bankIcon.setImageResource(R.mipmap.bank_abc);

                } else if (item.getBank_no() == 102804) {
                    bankName.setText("中国交通银行");
                    bankIcon.setImageResource(R.mipmap.bank_comm);

                } else if (item.getBank_no() == 102805) {
                    bankName.setText("中国银行");
                    bankIcon.setImageResource(R.mipmap.bank_boc);

                } else if (item.getBank_no() == 102806) {
                    bankName.setText("中国招商银行");
                    bankIcon.setImageResource(R.mipmap.bank_cmb);

                } else if (item.getBank_no() == 102807) {
                    bankName.setText("中国民生银行");
                    bankIcon.setImageResource(R.mipmap.bank_cmbc);

                } else if (item.getBank_no() == 102808) {
                    bankName.setText("浦东发展银行");
                    bankIcon.setImageResource(R.mipmap.bank_spdb);

                } else if (item.getBank_no() == 102809) {
                    bankName.setText("邮政储蓄银行");
                    bankIcon.setImageResource(R.mipmap.bank_psbc);

                } else if (item.getBank_no() == 102810) {
                    bankName.setText("中国中信银行");
                    bankIcon.setImageResource(R.mipmap.bank_citic);

                } else if (item.getBank_no() == 102811) {
                    bankName.setText("中国光大银行");
                    bankIcon.setImageResource(R.mipmap.bank_ceb);

                }
                bankNum.setText("尾号" + item.getBank_account());
                bankId = item.getId();
            }
        }
    }
}
