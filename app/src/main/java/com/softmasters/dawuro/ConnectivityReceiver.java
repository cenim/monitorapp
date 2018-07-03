package com.softmasters.dawuro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.softmasters.dawuro.services.CapturedDataService;
import com.softmasters.dawuro.utils.MonitorUtils;

public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, CapturedDataService.class);
        if(MonitorUtils.checkNetworkConnectivity(context)){
            context.startService(serviceIntent);
        }else{
            context.stopService(serviceIntent);
        }
    }
}
