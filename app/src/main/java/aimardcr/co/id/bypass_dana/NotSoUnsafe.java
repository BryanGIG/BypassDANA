package aimardcr.co.id.bypass_dana;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class NotSoUnsafe implements IXposedHookLoadPackage {

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
    public void initHook() {
        integrityCheck();
        emuCheck();
        hookCheck();
        rootCheck();
        tampCheck();
    }

    public void integrityCheck() {
        if (pClassLoader == null || context == null) {
            throw new NullPointerException("pClassLoader/context is null");
        }

        try {
            XposedHelpers.findAndHookMethod("id.dana.utils.IntegrityChecker", pClassLoader, "ArraysUtil", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(null);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to bypass integrityCheck!", Toast.LENGTH_SHORT).show();
        }
    }

    public void emuCheck() {
        if (pClassLoader == null || context == null) {
            throw new NullPointerException("pClassLoader/context is null");
        }

        try {
            XposedHelpers.findAndHookMethod("id.dana.DanaApplication", pClassLoader, "emuCb", long.class, long.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(null);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to bypass emuCheck!", Toast.LENGTH_SHORT).show();
        }
    }

    public void hookCheck() {
        if (pClassLoader == null || context == null) {
            throw new NullPointerException("pClassLoader/context is null");
        }

        try {
            XposedHelpers.findAndHookMethod("id.dana.DanaApplication", pClassLoader, "hookCb", long.class, long.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(null);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to bypass hookCheck!", Toast.LENGTH_SHORT).show();
        }
    }

    public void rootCheck() {
        if (pClassLoader == null || context == null) {
            throw new NullPointerException("pClassLoader/context is null");
        }

        try {
            XposedHelpers.findAndHookMethod("id.dana.DanaApplication", pClassLoader, "rootCb", long.class, long.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(null);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to bypass rootCheck!", Toast.LENGTH_SHORT).show();
        }
    }

    public void tampCheck() {
        if (pClassLoader == null || context == null) {
            throw new NullPointerException("pClassLoader/context is null");
        }

        try {
            XposedHelpers.findAndHookMethod("id.dana.DanaApplication", pClassLoader, "tampCb", long.class, long.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    param.setResult(null);
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(context, "Unable to bypass tampCheck!", Toast.LENGTH_SHORT).show();
        }
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
