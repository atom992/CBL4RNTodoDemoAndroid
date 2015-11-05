package com.example.atom.cbl4rntododemoandroid;

import android.content.Context;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.example.atom.cbl4rndemo02.CBLModule;
import com.facebook.react.bridge.NativeModule;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by atom on 15/10/27.
 */
public class CBLReactPackage extends MainReactPackage {

    private Context mContext;
    private CBLModule mModuleInstance;
    private final List<ReactPackage> mChildReactPackages = new ArrayList<>();

    CBLReactPackage(Context activityContext) {
        mContext = activityContext;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext   reactContext) {
        List<NativeModule> main = super.createNativeModules(reactContext);
        List<NativeModule> modules = new ArrayList<>();
        modules.addAll(main);
        modules.add(new CBLModule(reactContext,mContext));
        return modules;

//        mModuleInstance = new CBLModule(reactContext, mContext);
//        return Arrays.<NativeModule>asList(mModuleInstance);
    }

//    @Override
//    public List<Class<? extends JavaScriptModule>> createJSModules() {
//        return Collections.emptyList();
//    }
//
//    @Override
//    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
//        final Map<String, ViewManager> viewManagerMap = new HashMap<>();
//        for (ReactPackage reactPackage: mChildReactPackages) {
//            for (ViewManager viewManager: reactPackage.createViewManagers(reactContext)) {
//                viewManagerMap.put(viewManager.getName(), viewManager);
//            }
//        }
//        return new ArrayList(viewManagerMap.values());
//    }
}