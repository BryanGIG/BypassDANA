package io.github.bryangig.bypassdana;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class NotSoUnsafe implements IXposedHookLoadPackage {
    String TAG = "NotSoUnsafe";
    ClassLoader classLoader;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("id.dana")) {
            return;
        }

        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                classLoader = ((Application) param.thisObject).getClassLoader();
                XposedBridge.log("Hooked into " + lpparam.packageName);
                intentHook();
            }
        });
    }

    private void intentHook() {
        try {
            XposedHelpers.findAndHookMethod("id.dana.home.HomeTabActivity", classLoader, "onStart", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    try {
                        XposedHelpers.findField(Activity.class, "mCalled").set(param.thisObject, true);
                        param.setResult(null);
                    } catch (Exception e) {
                        XposedBridge.log(e);
                    }
                }
            });
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
