package com.bealder.sample;

import android.app.Application;
import android.graphics.Color;

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
    private BealderSDK bealderSDK;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialise Bealder
        bealderSDK = new BealderSDK(this);

        // Show debug in logcat
        //BealderParameters.setDebugMod();

        // Set Icon
        BealderParameters.setNotifIconL(R.drawable.logo_trans);
        BealderParameters.setNotifIcon(R.drawable.ic_launcher);
        BealderParameters.setNotifColorL(Color.parseColor("#2E6B7D"));

        // If Token Push, send it, any time
        //BealderParameters.setTokenPush(TOKEN_PUSH);

        // Set region to bootstrap
        regionBootstrap = new RegionBootstrap(this, bealderSDK.getRegion());

        bealderSDK.run(this);

    }

    @Override
    public void didEnterRegion(Region region) {
        bealderSDK.enterRegion(region);
    }

    @Override
    public void didExitRegion(Region region) {
        bealderSDK.exitRegion(region);
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        // Do nothing
    }
}
