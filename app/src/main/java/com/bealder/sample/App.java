package com.bealder.sample;

import android.app.Application;

import com.bealder.sdk.manager.BealderParameters;
import com.bealder.sdk.manager.BealderSDK;

import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 * Created by jed on 27/05/2015.
 */
public class App extends Application implements BootstrapNotifier {

    private RegionBootstrap regionBootstrap;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialise Bealder
        BealderSDK bealderSDK = BealderSDK.getInstance(this);

        // Show debug in logcat
        //BealderParameters.setDebugMod();

        // Set Icon
        BealderParameters.setLogo(R.drawable.logo);

        // If Token Push, send it, any time
        //BealderParameters.setTokenPush(TOKEN_PUSH);

        // Set region to bootstrap
        regionBootstrap = new RegionBootstrap(this, bealderSDK.getRegion());

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
