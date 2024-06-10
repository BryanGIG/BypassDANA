package io.github.bryangig.bypassdana;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class NotSoUnsafe implements IXposedHookLoadPackage {
    String TAG = "NotSoUnsafe";

    @Override
    public void handleLoadPackage(@NonNull final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("id.dana")) {
            return;
        }

        intentHook();
    }

    private void intentHook() {
        try {
            XposedHelpers.findAndHookMethod(ContextWrapper.class, "startActivity", Intent.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Intent intent = (Intent) param.args[0];
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String str = bundle.getString("unsafe_status");
                        if (str != null) {
                            XposedBridge.log("unsafe_status: " + str);
                            XposedBridge.log("Bye bye, unsafe!");
                            param.setResult(null);
                        }
                    }
                }
            });
        } catch (Throwable e) {
            XposedBridge.log(e);
        }
    }
}
