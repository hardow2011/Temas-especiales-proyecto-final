package com.example.proyectofinal.utils;

import android.content.Context;
import com.example.proyectofinal.R;

import com.kaopiz.kprogresshud.KProgressHUD;

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

    public KProgressHUD showDownload() {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getString(R.string.please_wait))
                .setDetailsLabel(context.getString(R.string.download))
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public KProgressHUD showUpload() {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getString(R.string.please_wait))
                .setDetailsLabel(context.getString(R.string.upload))
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
