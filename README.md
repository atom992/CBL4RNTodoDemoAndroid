# ToDo Lite for React Native (Android)

This demo app to show how to use [Couchbase Lite](http://developer.couchbase.com/documentation/mobile/1.1.0/get-started/couchbase-lite-overview/index.html) in a [React Native android](http://facebook.github.io/react-native/) app and sync docs with [Sync Gateway](http://developer.couchbase.com/documentation/mobile/1.1.0/get-started/sync-gateway-overview/index.html) base on [ToDo Lite for React Native](https://github.com/jamiltz/ToDoLite-ReactNative) Step by Step.
The main step as following:

- Create a basic project with Couchbase Lite Android and Couchbase Lite java Listener.
- Integrating React Native with basic project
- Add JS to project
- Setting up Sync Gateway
- Run the Demo app

The result will be looks like this:
![image](https://github.com/atom992/CBL4RNTodoDemoAndroid/blob/master/README_RES/CBL4RNTodoDemoAndroid.gif)

# Create a basic project with CBL Android and CBL java Listener

reference [Create a new project with Android Studio](http://developer.couchbase.com/documentation/mobile/1.1.0/get-started/get-started-mobile/android/get-started-studio/index.html)

## Set up the environment

Before you can build an app, you need to set up your development environment:

1. Download and install [Android Studio](http://developer.android.com/sdk/installing/studio.html).

2. Launch Android Studio.

3. From the Quick Start menu on the welcome screen, select Configure > SDK Manager.
If you already have a project open, you can open the SDK Manager by selecting Tools > Android > SDK Manager from the Android Studio menu bar.

4. In Android SDK Manager, select the following items and then click Install packages:
```
Tools/Android SDK Tools
Tools/Android SDK Platform-tools
Tools/Android SDK Build-tools
Android API (currently recommended: API 19)
Extras/Google Repository
Extras/Android Support Repository
```

## Create a new project
1. Launch Android Studio.

2. In the Welcome to Android Studio screen, choose New Project.

3. In the New Project window, enter the application name, module name, package name, and project location.
This example uses **CBL4RNTodoDemoAndroid** for the application name. It should look something like this:
![image](https://github.com/atom992/CBL4RNTodoDemoAndroid/blob/master/README_RES/Configure_your_new_project_Step_1.png)

4. Set the minimum required SDK to [API 16: Android 4.1 or later](https://github.com/facebook/react-native#react-native---) and use the currently recommended Android API.
After you fill in the fields, the New Project window should look something like this:
![image](https://github.com/atom992/CBL4RNTodoDemoAndroid/blob/master/README_RES/Configure_your_new_project_Step_2.png)

5. Click Next, and then move through the remaining setup screens and enter settings as necessary (or just accept the defaults).It should look something like this:
![image](https://github.com/atom992/CBL4RNTodoDemoAndroid/blob/master/README_RES/Configure_your_new_project_Step_3.png)

6. Click Finish.It should look something like this:
![image](https://github.com/atom992/CBL4RNTodoDemoAndroid/blob/master/README_RES/Configure_your_new_project_Step_3n.png)

## Add Couchbase Lite Lib Dependencies
1. Expand the app folder, and then open the build.gradle file.
If the build.gradle does not contain an android section, then you are looking at the wrong one. Make sure you open the one in the **CBL4RNTodoDemoAndroid** folder (and not the one at the project level).
2. Add the following lines to the android section:
```
// workaround for "duplicate files during packaging of APK" issue
// see https://groups.google.com/d/msg/adt-dev/bl5Rc4Szpzg/wC8cylTWuIEJ
packagingOptions {
    exclude 'META-INF/ASL2.0'
    exclude 'META-INF/LICENSE'
    exclude 'META-INF/NOTICE'
}
```
3. Expand the project folder, and then open the build.gradle file. Add the following lines to the buildscript section, the buildscript section should look similar to this::
```
buildscript {
    repositories {
        jcenter()
        maven {
            url "http://files.couchbase.com/maven2/"
        }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.1.0'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
```

4. Download [libs](https://github.com/couchbaselabs/Couchbase-Lite-PhoneGap-Plugin/tree/master/lib/android) and add libs (with out jackson-core-2.5.0.jar) into <project_home>/app/libs.

Note: Couchbase-Lite-java using jackson-core-2.5.0.jar will conflict with React Native(using jackson-core-2.2.3.jar).
After you add the libs, add the following line to the top-level dependencies section (not the one under the buildscript section), the dependencies section should look similar to this:
```
dependencies {
    compile 'com.android.support:appcompat-v7:23.0.1'
    compile 'com.fasterxml.jackson.core:jackson-core:2.5.0'
//    compile 'com.couchbase.lite:couchbase-lite-android:1.1.0'
//    compile 'com.couchbase.lite:couchbase-lite-java-core:1.1.0'
//    compile 'com.couchbase.lite:couchbase-lite-java-listener:1.1.0'
//    compile 'com.couchbase.lite:couchbase-lite-java-javascript:1.1.0'
    compile fileTree(dir: 'libs', include: ['*.jar'])

}
```
Note: There should be better way to add libs.

# Integrating React Native with basic project
reference [Integrating with Existing Apps](https://facebook.github.io/react-native/docs/embedded-app-android.html#content)
## Requirements
* an existing, gradle-based Android app
* Node.js, see Getting Started for setup instructions

## Prepare your app
1. Expand the app folder, and then open the build.gradle file. Add the React Native dependency:
```
compile 'com.facebook.react:react-native:0.13.0'
```
after add the dependency, the dependencies section should look similar to this:
```
dependencies {
    compile 'com.android.support:appcompat-v7:23.0.1'
    compile 'com.fasterxml.jackson.core:jackson-core:2.5.0'
//    compile 'com.couchbase.lite:couchbase-lite-android:1.1.0'
//    compile 'com.couchbase.lite:couchbase-lite-java-core:1.1.0'
//    compile 'com.couchbase.lite:couchbase-lite-java-listener:1.1.0'
//    compile 'com.couchbase.lite:couchbase-lite-java-javascript:1.1.0'
    compile 'com.facebook.react:react-native:0.13.0'
    compile fileTree(dir: 'libs', include: ['*.jar'])
}
```
2. In the Android Studio tool bar, click Sync Project with Gradle Files.
after build, It should look similar to this:
![image](https://github.com/atom992/CBL4RNTodoDemoAndroid/blob/master/README_RES/Gradle_Build_Result.png)
3. Expand the app/src/main folder, and then open the AndroidManifest.xml file. Add the Internet permission:
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```
This is only really used in dev mode when reloading JavaScript from the development server, so you can strip this in release builds if you need to.
Note: you should [add ACCESS_NETWORK_STATE permission](http://stackoverflow.com/questions/12778168/access-network-state-permisson-on-android-ics), or you might be get this error:
```
java.lang.SecurityException: ConnectivityService: Neither user 10093 nor current process has android.permission.ACCESS_NETWORK_STATE
```
 when sync docs.
 Add following code into activity section:
```
<activity
   android:name=".MyReactActivity"
   android:label="@string/app_name" >
   <intent-filter>
       <action android:name="android.intent.action.MAIN" />

       <category android:name="android.intent.category.LAUNCHER" />
   </intent-filter>
</activity>
```

## Add native code
You need to add some native code in order to start the React Native runtime and get it to render something. To do this, we're going to create an Activity that creates a ReactRootView, starts a React application inside it and sets it as the main content view.
1. New a class under <project_home>/app/src/main/java/com/example/atom/cbl4rntododemo  folder,named MyReactActivity.java:

```
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

```
Note: line 71 set black user/password for simplify logic to react native.

# Add JS to project
1. In your project's root folder, run:
```
$ npm init
$ npm install --save react-native
$ curl -o .flowconfig https://raw.githubusercontent.com/facebook/react-native/master/.flowconfig
```
This creates a node module for your app and adds the react-native npm dependency. Now open the newly created package.json file and add this under scripts:
```
"start": "node_modules/react-native/packager/packager.sh"
```

2. Add JS code to project.
Copy & paste the following code to index.android.js in your root folder


```
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

var React = require('react-native');
var Home = require('./app/components/Home');
var {
  AppRegistry,
  StyleSheet,
  Text,
  View
} = React;


var styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#111111'
  }
});


var CBL4RNTodoDemoAndroid = React.createClass({
  render: function() {
    return (
      <Home/>
    );
  }
});

AppRegistry.registerComponent('CBL4RNTodoDemoAndroid', () => CBL4RNTodoDemoAndroid);

```

Copy & paste the following code to Home.js in <project_home>/app/components folder
```
var React = require('react-native');
var api = require('./../utils/api');

var {
  Text,
  View,
  StyleSheet,
  ScrollView,
  TextInput,
  TouchableOpacity
  } = React;

var styles = StyleSheet.create({
    container: {
        flex: 1
    },
    buttonText: {
        fontSize: 18,
        color: 'white',
        alignSelf: 'center'
    },
    rowContainer: {
        padding: 10
    },
    rowTitle: {
        color: '#48BBEC',
        fontSize: 16
    },
    rowContent: {
        fontSize: 19
    },

    mainContainer: {
        flex: 1,
        padding: 30,
        marginTop: 65,
        flexDirection: 'column',
        justifyContent: 'center',
        backgroundColor: '#48BBEC'
    },
    searchInput: {
        height: 50,
        padding: 4,
        marginRight: 5,
        fontSize: 23,
        borderWidth: 1,
        borderColor: 'white',
        borderRadius: 8,
        color: 'white',
        margin: 5
    },
    buttonText: {
        fontSize: 18,
        color: '#111',
        alignSelf: 'center'
    },
    button: {
        height: 45,
        flexDirection: 'row',
        backgroundColor: 'white',
        borderColor: 'white',
        borderWidth: 1,
        borderRadius: 8,
        marginBottom: 10,
        marginTop: 10,
        alignSelf: 'stretch',
        justifyContent: 'center'
    },
});

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            newTodo: '',
            todos: []
        };
    }
    componentWillMount() {
        api.getTodos()
          .then((res) => {
              var todos = res.rows.map(function (row) {
                  return row.doc;
              });
              this.setState({
                  todos: todos
              });
          });
    }
    handleTodoChange(event) {
        this.setState({
            newTodo: event.nativeEvent.text
        });
    }
    handleSave() {
        api.saveTodo(this.state.newTodo)
        .then((res) => {
              api.getTodos()
                .then((res) => {
                    var todos = res.rows.map(function (row) {
                        return row.doc;
                    });
                    this.refs.inputText.value = '';
                    this.setState({
                        todos: todos,
                        newTodo: ''
                    });
                });
          });
    }
    handleSync() {
        api.startSync()
          .then(function(res) {
              console.log(res);
          });
    }
    render() {
        var lists = this.state.todos.map((item, index) => {
            return (
              <View key={index}>
                  <View style={styles.rowContainer}>
                      <Text style={styles.rowContent}> {item.title} </Text>
                  </View>
              </View>
            );
        });
        return (
          <View style={styles.mainContainer}>
              <TextInput
                ref='inputText'
                value={this.state.newTodo}
                onChange={this.handleTodoChange.bind(this)}
                style={styles.searchInput}/>
              <TouchableOpacity onPress={this.handleSave.bind(this)} style={styles.button}>
                  <Text style={styles.buttonText}>Save</Text>
              </TouchableOpacity>
              <TouchableOpacity onPress={this.handleSync.bind(this)} style={styles.button}>
                  <Text style={styles.buttonText}>Sync</Text>
              </TouchableOpacity>
              <ScrollView style={styles.container}>
                  {lists}
              </ScrollView>
          </View>
        );
    }
}

Home.propTypes = {
    lists: React.PropTypes.array.isRequired
};

module.exports = Home;
```
Copy & paste the following code to api.js in <project_home>/app/utils folder
```
var api = {
    url: 'http://127.0.0.1:5984',
    saveTodo(title){
        return fetch(this.url + '/todos/', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                type: 'list',
                title: title
            })
        }).then((res) => res.json());
    },



    getTodos(){
            return fetch(this.url + '/todos/_all_docs?include_docs=true').then((response) => {
              if (response.status !== 200) {

                return fetch('http://127.0.0.1:5984/todos', {
                                            method:'PUT',
                                            headers: {
                                                'Accept': 'application/json',
                                                'Content-Type': 'application/json'
                                            },
                                            body: JSON.stringify({ok:true})
                                        }).then((res) => res.json());
              }
              return response.json();
            })
        },

    startSync(){
        return fetch(this.url + '/_replicate', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                source: 'todos',
                target: 'http://localhost:4984/todos/',
                continuous: true
            })
        }).then((res) => res.json());
    }
};

module.exports = api;


```
Note：url in api.js should match the address of CBL in MyReactActivity.java;
      target in api.js is the sync gateway's url;

# Setting up Sync Gateway
Download Sync Gateway and unzip the file:
```
http://www.couchbase.com/nosql-databases/downloads#Couchbase\_Mobile
```

Start Sync Gateway with the config file in the root of the repository:
```
$ ~/Downloads/couchbase-sync-gateway/bin/sync_gateway <project_home>/sync-gateway-config.json
```
Open the Admin Dashboard to monitor the documents that were saved to Sync Gateway.

http://localhost:4985/_admin/
# Run the Demo app
To run your app, you need to first start the development server. To do this, simply run the following command in your root folder:
```
npm start
```
Now build and run your Android app in other term:
```
./gradlew installDebug
```
Note:  If you run the demo on the emulator, you should run the following command:
```
adb reverse tcp:4984 tcp:4984
```
Once you reach your React-powered activity inside the app, it should load the JavaScript code from the development server and display.
