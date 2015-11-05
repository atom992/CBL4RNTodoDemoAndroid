package com.example.atom.cbl4rntododemoandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


//import com.facebook.CallbackManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.uimanager.IllegalViewOperationException;

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
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by atom on 15/10/27.
 */
public class CBLModule extends ReactContextBaseJavaModule {

    private Context mActivityContext;
//    private CallbackManager mCallbackManager;

    private final String TAG = "CBLModule";
    private  String CBL_URL = "url_in_CBL_Module";
    private static final String DB_NAME = "cbldb";
    private static final int DEFAULT_LISTEN_PORT = 5984;
    private boolean inited = false;
    private int listenPort;
    private Credentials allowedCredentials;


    public CBLModule(ReactApplicationContext reactContext, Context activityContext) {
        super(reactContext);
        Log.d(TAG,"Calling CBLModule");
        mActivityContext = activityContext;
        if(!inited){
            initCBLite(mActivityContext);
        }
    }

//    public CBLModule(ReactApplicationContext reactContext) {
//        super(reactContext);
//        Log.d(TAG,"Calling CBLModule");
//        if(!inited){
//            initCBLite(reactContext);
//        }
//    }

//    public boolean handleActivityResult(final int requestCode, final int resultCode, final Intent data) {
//        return mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public String getName() {
        Log.d(TAG,"getName CBLModule");
        return "CBLModule";
    }

    @ReactMethod
    public void getUrl(
            Callback errorCallback,
            Callback successCallback) {
        try {
            Log.d(TAG,"return CBL url to JS:" + CBL_URL);
            successCallback.invoke(CBL_URL);
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    private void initCBLite(Context context) {
        try {

            allowedCredentials = new Credentials();

//            allowedCredentials = new Credentials("cbluser","password");

//            allowedCredentials = new Credentials("","");

            URLStreamHandlerFactory.registerSelfIgnoreError();

            View.setCompiler(new JavaScriptViewCompiler());

            Manager server = startCBLite(new AndroidContext(context));

            listenPort = startCBLListener(DEFAULT_LISTEN_PORT, server, allowedCredentials);
            if(listenPort == 9527){
                server = null;
                listenPort =  DEFAULT_LISTEN_PORT;
            }

            if(!allowedCredentials.getLogin().equalsIgnoreCase("") && !allowedCredentials.getPassword().equalsIgnoreCase("")){
                CBL_URL = String.format(
                        "http://%s:%s@localhost:%d/",
                        allowedCredentials.getLogin(),
                        allowedCredentials.getPassword(),
                        listenPort);
            }else{
                CBL_URL = "http://127.0.0.1:5984";
            }
//            CBLObj cblObj = new CBLObj();
//            cblObj.setURL(String.format(
//                    "http://%s:%s@localhost:%d/",
//                    allowedCredentials.getLogin(),
//                    allowedCredentials.getPassword(),
//                    listenPort));
            inited = true;
            Log.i(TAG, "initCBLite() completed successfully with: " + CBL_URL);
        } catch (final Exception e) {
            e.printStackTrace();
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
        if(boundPort != DEFAULT_LISTEN_PORT){
            boundPort = 9527;
            listener.stop();
        }else{
            Thread thread = new Thread(listener);
            thread.start();
        }
        return boundPort;

    }
}
