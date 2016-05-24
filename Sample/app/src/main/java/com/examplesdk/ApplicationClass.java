package com.examplesdk;

import android.app.Application;
import android.graphics.Color;

import com.bealder.sdk.manager.BealderParameters;
import com.bealder.sdk.manager.BealderSDK;

import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 * Created by Marty on 24/05/2016.
 */
public class ApplicationClass extends Application implements BootstrapNotifier {

    private RegionBootstrap regionBootstrap;
    private BealderSDK bealderSDK;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialise Bealder - require -
        bealderSDK = new BealderSDK(this);

        // Show debug in logcat
        BealderParameters.setDebugMod();

        // Set Icon - require -
        BealderParameters.setNotifIcon(R.mipmap.ic_launcher);
        BealderParameters.setNotifIconL(R.drawable.ic_stat_name);
        BealderParameters.setNotifColorL(Color.parseColor("#357f77"));

        // If Token Push, send it, any time
        //BealderParameters.setTokenPush(TOKEN_PUSH);

        // Set region to bootstrap - require -
        regionBootstrap = new RegionBootstrap(this, bealderSDK.getRegion());

        // - require -
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
