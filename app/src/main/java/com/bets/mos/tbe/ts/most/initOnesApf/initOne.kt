package com.bets.mos.tbe.ts.most.initOnesApf

import android.app.Application
import com.bets.mos.tbe.ts.most.fly
import com.onesignal.OneSignal
import org.json.JSONObject

class initOne: Application() {



    override fun onCreate() {
        super.onCreate()
        try {

            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
            OneSignal.initWithContext(this)
            OneSignal.setAppId(keystore.oneKey)
            OneSignal.setExternalUserId(fly!!, object :
                OneSignal.OSExternalUserIdUpdateCompletionHandler {
                override fun onSuccess(jsonObject: JSONObject) {

                }
                override fun onFailure(externalIdError: OneSignal.ExternalIdError) {

                }
            })


        }catch (e: Exception){

        }
    }
}