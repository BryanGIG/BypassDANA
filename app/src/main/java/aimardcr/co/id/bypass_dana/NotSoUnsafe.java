package aimardcr.co.id.bypass_dana;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class NotSoUnsafe implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("id.dana")) {
            return;
        }

        XposedHelpers.findAndHookMethod("id.dana.utils.IntegrityChecker", lpparam.classLoader, "ArraysUtil$2", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Bypassing IntegrityChecker.ArraysUtil$2: " + param.args[0]);
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod("id.dana.DanaApplication", lpparam.classLoader, "emuCb", long.class, long.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod("id.dana.DanaApplication", lpparam.classLoader, "hookCb", long.class, long.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod("id.dana.DanaApplication", lpparam.classLoader, "rootCb", long.class, long.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod("id.dana.DanaApplication", lpparam.classLoader, "tampCb", long.class, long.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });
    }
}
