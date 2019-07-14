package com.store.hasanfadool.mystore.network;

import com.store.hasanfadool.mystore.BuildConfig;

public class GetDomin {


                                            // the ip of the server
    public final String myIpPort(){

        if (BuildConfig.DEBUG){
            return "http://localhost:8080";
        }
        else {
            return "http://" + "192.168.1.6" + ":" + "8080";
        }
    }



}
