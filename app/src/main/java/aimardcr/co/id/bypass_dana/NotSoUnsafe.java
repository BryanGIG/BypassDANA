package aimardcr.co.id.bypass_dana;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class NotSoUnsafe implements IXposedHookLoadPackage {
    String TAG = "NotSoUnsafe";

    // target app classloader & context
    private ClassLoader pClassLoader;
    private Context context;

    @Override
    public void handleLoadPackage(@NonNull final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("id.dana")) {
            return;
        }

        pClassLoader = lpparam.classLoader;
        initAppContext();
    }

    private void intentHook() {
        try {
            XposedHelpers.findAndHookMethod(ContextWrapper.class, "startActivity", Intent.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Intent intent = (Intent) param.args[0];
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String str = bundle.getString("unsafe_status");
                        if (str != null) {
                            param.setResult(null);
                        }
                    }
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to bypass detection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void initHook() {
        if (pClassLoader == null || context == null) {
            throw new NullPointerException("pClassLoader/context is null");
        }

        intentHook();
    }

    public void initAppContext() {
        try {
            XposedHelpers.findAndHookMethod(Application.class.getName(), null, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    context = (Context) param.args[0];
                    initHook();
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
