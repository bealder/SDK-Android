# BealderSDK

## Preparation

* 	Get your **APP_ID** and **APP_KEY** from [app.bealder.com](https://app.bealder.com)

## Configure your project with Eclipse (deprecated)

*	Add the `bealder_sdk_1.0.jar` into your Project's libs folder  

*	You can also add the javadoc and a properties file `bealder_sdk_1.0.jar.properties` with the path of the javadoc folder, for example:

```Ini
doc=docs/bealder_sdk
```

## Configure your project with Android Studio

### SDK Jar file (deprecated)

*	Jar file can be added anywhere, generaly under a `libs` or `libraries` folder and contains all the dependencies

*	The `build.gradle` settings (assuming we store libraries in the `libraries` folder):

```
dependencies {
    compile fileTree(dir: 'libraries', include: ['*.jar'])
    compile files 'libraries/bealder_sdk_1.0.jar'
    compile 'com.android.support:support-v4:21.0.3'
}
```

### SDK Aar file

#### Import method

*	Aar file is used as a new module and doesn't contains external dependencies (only parse libraries):

 File > New module... > Import .Jar or .Aar package

*	The `build.gradle` settings (assuming we store libraries module in the `libraries` folder):

```
dependencies {
    compile fileTree(dir: 'libraries', include: ['*.jar'])
    compile 'com.mcxiaoke.volley:library:1.0.11'
    compile 'com.android.support:support-v4:21.0.3'
    compile 'org.altbeacon:android-beacon-library:2.1.3'
    compile project(':BealderSdk')
}
```

> **Note**: The module should already be added in `settings.gradle` by Android Studio during the import process.

#### Local repository method

* Make sure you have a directory declared for your local repository in the `build.gradle` of your project, in our case we'll use `libraries` (could be anything)

```
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs '../libraries'
        }
    }
}
```

* The `build.gradle` settings of your module:

```
dependencies {
    compile 'com.bealder.android.sdk:bealderSdk:@aar'
    compile 'com.mcxiaoke.volley:library:1.0.15'
    compile 'com.android.support:support-v4:22.1.1'
    compile 'org.altbeacon:android-beacon-library:2.1.4'
    compile 'com.android.support:appcompat-v7:22.1.1'
}
```

## Commons part to both IDEs

*	Add the following elements to your `manifest.xml`:

####	Permissions

```XML
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
```

####	Under application

```XML
<!-- API_KEY Bealder -->
<meta-data
    android:name="com.bealder.sdk.API_ID"
    android:value="APP_ID" />
<meta-data
    android:name="com.bealder.sdk.API_KEY"
    android:value="APP_KEY" />
<activity android:name="com.bealder.android.sdk.AdvertActivity" >
</activity>

```

*	Class Application

> Create class application if it doesn't exist and add declaration to your `manifest.xml`:

```XML
	<application
        android:name=".ApplicationClass"

```

> In your class application, implements __BootstrapNotifier__ and set BealderSDK

```Java
import android.app.Application;
import com.bealder.sdk.manager.BealderParameters;
import com.bealder.sdk.manager.BealderSDK;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

public class ApplicationClass extends Application implements BootstrapNotifier {

    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialise Bealder - require -
        BealderSDK bealderSDK = BealderSDK.getInstance(this);

        // Show debug in logcat
        //BealderParameters.setDebugMod();

        // Set Icon - require -
        BealderParameters.setLogo(R.drawable.logo);

        // If Token Push, send it, any time
        //BealderParameters.setTokenPush(TOKEN_PUSH);

        // Set region to bootstrap - require -
        regionBootstrap = new RegionBootstrap(this, bealderSDK.getRegion());

        // - require -
        BealderSDK.run(this);

    }

    @Override
    public void didEnterRegion(Region region) {
        BealderSDK.enterRegion(region);
    }

    @Override
    public void didExitRegion(Region region) {
        BealderSDK.exitRegion(region);
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        // Do nothing
    }
}
```
*	Principal Activity

 >	You can start to use the app but if you need more informations some methods need to be overridden:

```Java
import com.bealder.sdk.manager.BealderParameters;
import com.bealder.sdk.manager.BealderSDK;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.bealder.R.layout.activity_first);

        // - require -
        BealderParameters.startApp(this);

        // If you want ask BLE activation
        //BealderSDK.askBleActivation(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // - require -
        BealderParameters.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // - require -
        BealderParameters.onStop();
    }
```