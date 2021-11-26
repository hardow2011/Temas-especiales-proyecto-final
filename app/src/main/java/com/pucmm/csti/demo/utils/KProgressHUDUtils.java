package com.pucmm.csti.demo.utils;

import android.content.Context;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pucmm.csti.R;

public class KProgressHUDUtils {

    private Context context;

    public KProgressHUDUtils(Context context) {
        this.context = context;
    }

    public KProgressHUD showConnecting() {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getString(R.string.please_wait))
                .setDetailsLabel(context.getString(R.string.connecting))
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }

    public KProgressHUD showAuthenticating() {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getString(R.string.please_wait))
                .setDetailsLabel(context.getString(R.string.authenticating))
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }
}