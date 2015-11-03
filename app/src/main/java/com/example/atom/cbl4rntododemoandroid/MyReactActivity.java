package com.example.atom.cbl4rntododemoandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;


import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Database;
import com.couchbase.lite.listener.LiteListener;
import com.couchbase.lite.listener.LiteServlet;
import com.couchbase.lite.listener.Credentials;
import com.couchbase.lite.router.URLStreamHandlerFactory;
import com.couchbase.lite.View;
import com.couchbase.lite.javascript.JavaScriptViewCompiler;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.io.File;

/**
 * Created by atom on 15/10/27.
 */
public class MyReactActivity extends Activity implements DefaultHardwareBackBtnHandler {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    private final String TAG = "CBLEvents";
    private  String CBL_URL = "http://127.0.0.1:5984";
    private static final String DB_NAME = "cbldb";
    private static final int DEFAULT_LISTEN_PORT = 5984;
    private boolean initFailed = false;
    private int listenPort;
    private Credentials allowedCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"Begin onCreate Events App");

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "CBL4RNTodoDemoAndroid", null);

        setContentView(mReactRootView);
        initCBLite();
        Log.d(TAG, "End onCreate Events App");
    }

    private void initCBLite() {
        try {

//            allowedCredentials = new Credentials();

            allowedCredentials = new Credentials("","");

            URLStreamHandlerFactory.registerSelfIgnoreError();

            View.setCompiler(new JavaScriptViewCompiler());

            Manager server = startCBLite(new AndroidContext(this));

            listenPort = startCBLListener(DEFAULT_LISTEN_PORT, server, allowedCredentials);



            Log.i(TAG, "initCBLite() completed successfully with: " + String.format(
                    "http://%s:%s@localhost:%d/",
                    allowedCredentials.getLogin(),
                    allowedCredentials.getPassword(),
                    listenPort));

            CBL_URL = String.format(
                    "http://%s:%s@localhost:%d/",
                    allowedCredentials.getLogin(),
                    allowedCredentials.getPassword(),
                    listenPort);


        } catch (final Exception e) {
            e.printStackTrace();
            initFailed = true;
        }

    }

    protected Manager startCBLite(AndroidContext context) {
        Manager manager;
        try {
            Manager.enableLogging(Log.TAG, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_SYNC, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_QUERY, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_VIEW, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_CHANGE_TRACKER, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_BLOB_STORE, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_DATABASE, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_LISTENER, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_MULTI_STREAM_WRITER, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_REMOTE_REQUEST, Log.VERBOSE);
            Manager.enableLogging(Log.TAG_ROUTER, Log.VERBOSE);
            manager = new Manager(context, Manager.DEFAULT_OPTIONS);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return manager;
    }

    private int startCBLListener(int listenPort, Manager manager, Credentials allowedCredentials) {

        LiteListener listener = new LiteListener(manager, listenPort, allowedCredentials);
        int boundPort = listener.getListenPort();
        Thread thread = new Thread(listener);
        thread.start();
        return boundPort;

    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onResume(this);
        }
    }


    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}