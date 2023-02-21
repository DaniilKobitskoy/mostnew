package com.bets.mos.tbe.ts.most

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.bets.mos.tbe.ts.most.databinding.ActivityMainBinding
import com.bets.mos.tbe.ts.most.initOnesApf.keystore
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.json.JSONObject
import java.util.*





var fly: String? = null
lateinit var bm: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    lateinit var dataPhone: Map<String, Any>
    lateinit var cohfigs: FirebaseRemoteConfig
    var dp: String? = null
    var urls1: String? = null
    var uri2: String? = null
    var configweb: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bm = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bm.root)
        supportFragmentManager
            .beginTransaction()
            .replace(bm.mainView.id, menu())
            .commit()
        val getfly = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        fly = getfly
        val AnimationView = AnimationUtils.loadAnimation(this, R.anim.loadingviews)
        bm.textView2.startAnimation(AnimationView)
        AnimationView.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(starts: Animation?) {
            }

            override fun onAnimationEnd(starts: Animation?) {
                firebaseInitSGK()

            }

            override fun onAnimationRepeat(starts: Animation?) {
            }
        })




    }

    private fun firebaseInitSGK() {
        cohfigs = FirebaseRemoteConfig.getInstance()
        val getBase = FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(3600).build()
        cohfigs.setConfigSettingsAsync(getBase)
        cohfigs.setDefaultsAsync(R.xml.remote_config_defaults)
        cohfigs.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val webInfo: Boolean = cohfigs.getBoolean("WebOpen")
                    if (webInfo == true) {
                        urls1 = cohfigs.getString("links1")
                        uri2 = cohfigs.getString("links2")

                        OpenWebEvents(fly!!)
                        fbSDK()
                        FlyersSDK()


                    } else {
                        bm.mainView.visibility = View.VISIBLE
                        bm.webview.visibility = View.GONE
                        bm.lot.visibility = View.GONE

                        bm.textView2.visibility = View.GONE
                    }

                } else {
                    Toast.makeText(this, "Retry Connected", Toast.LENGTH_SHORT).show()

                }

            }
    }

    private fun FlyersSDK() {
        AppsFlyerLib.getInstance().setDebugLog(true)

        val peopleInfo: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(DataOrganic: Map<String, Any>) {
                try {
                    dataPhone = DataOrganic
                    checkOpensWebViews()
Log.d("tyttyt1", "tyttyt1")

                } catch (errors: Exception) {
                    errors.printStackTrace()
                }
            }

            override fun onConversionDataFail(erSub: String) {
                runOnUiThread {
                    errorVolley()
                    bm.mainView.visibility = View.VISIBLE
                    bm.webview.visibility = View.GONE
                    bm.lot.visibility = View.GONE
                    bm.textView2.visibility = View.GONE
                }
            }

            override fun onAppOpenAttribution(erSubData: Map<String, String>) {
                runOnUiThread {
                    errorVolley()
                    bm.mainView.visibility = View.VISIBLE
                    bm.webview.visibility = View.GONE
                    bm.lot.visibility = View.GONE
                    bm.textView2.visibility = View.GONE
                }
            }

            override fun onAttributionFailure(erSubAtrib: String) {
                runOnUiThread {
                    errorVolley()
                    bm.mainView.visibility = View.VISIBLE
                    bm.webview.visibility = View.GONE
                    bm.lot.visibility = View.GONE
                    bm.textView2.visibility = View.GONE
                }
            }
        }
        AppsFlyerLib.getInstance().init(keystore.apsKey, peopleInfo, this)
        AppsFlyerLib.getInstance().registerConversionListener(this, peopleInfo)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun errorVolley() {

        val errorsVolley1 = Volley.newRequestQueue(this)
        val errorInfo = JSONObject()
        errorInfo.put("name", "a_o_e")
        val errorInfo2 = JSONObject()
        errorInfo2.put("success", true)
        errorInfo.put("data", errorInfo2)
        errorInfo.put("created", countryTime())
        var eventGetError = uri2

        val postResponse = object: JsonObjectRequest(
            Request.Method.POST, eventGetError, errorInfo,
            { response ->

            }, { error ->

            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val errorHead: MutableMap<String, String> = HashMap()
                errorHead["Device-UUID"] = fly!!
                return errorHead
            }
        }
        errorsVolley1.add(postResponse)

    }

    private fun checkOpensWebViews() {
        Log.d("tyttyt2", "tyttyt2")

        val mainWindows = Volley.newRequestQueue(this)
        val jsMain = JSONObject()
        jsMain.put("appsFlyerId", fly)
        val MainView = JSONObject(dataPhone)
        jsMain.put("apsInfo", MainView)
        jsMain.put("deeplink", if(dp == null) JSONObject.NULL else dp)
        var LinkedUrlMain = urls1
        val jsMains = object: JsonObjectRequest(
            Request.Method.POST, LinkedUrlMain, jsMain,
            { response ->
                if(response.getBoolean("success")) {
                    Log.d("tyttyt3", "tyttyt3")

                    configweb = true
                    bm.webview.settings.javaScriptEnabled = true
                    bm.webview.settings.domStorageEnabled = true
                    bm.webview.settings.useWideViewPort = true
                    bm.webview.settings.loadWithOverviewMode = true
                    bm.webview.settings.allowFileAccess = true
                    bm.webview.settings.javaScriptCanOpenWindowsAutomatically = true
                    bm.webview.settings.setSupportMultipleWindows(false)
                    bm.webview.settings.displayZoomControls = false
                    bm.webview.settings.builtInZoomControls = true
                    bm.webview.settings.setSupportZoom(true)
                    bm.webview.settings.pluginState = WebSettings.PluginState.ON
                    bm.webview.settings.mixedContentMode = 0
                    bm.webview.settings.allowContentAccess = true
                    CookieManager.getInstance().setAcceptCookie(true)
                    CookieManager.getInstance().setAcceptThirdPartyCookies(bm.webview, true)

                    bm.webview.webViewClient = object : WebViewClient() {
                        override fun onPageStarted(dsfghf: WebView?, str: String?, map: Bitmap?) {
                            super.onPageStarted(dsfghf, str, map)

                        }

                        override fun onPageFinished(v: WebView, links: String) {
                            bm.webview.visibility = View.VISIBLE
                            bm.mainView.visibility = View.GONE
                            bm.lot.visibility = View.GONE

                            bm.textView2.visibility = View.GONE
                            installInfo(links)

                        }

                        override fun onReceivedHttpError(
                            webStr: WebView?,
                            zapros: WebResourceRequest?,
                            reqsaint: WebResourceResponse?
                        ) {
                            super.onReceivedHttpError(webStr, zapros, reqsaint)
                            ErorrsMains( reqsaint!!.statusCode.toString())

                        }

                        @RequiresApi(Build.VERSION_CODES.M)
                        override fun onReceivedError(weidbew: WebView?, setmains: WebResourceRequest?, info: WebResourceError?) {
                            super.onReceivedError(weidbew, setmains, info)
                            ErorrsMains( info.toString())
                        }
                    }
                    bm.webview.loadUrl(response.getString("url"))

                }
                else{
                    bm.webview.visibility = View.GONE
                    bm.mainView.visibility = View.VISIBLE
                    bm.lot.visibility = View.GONE
                    bm.textView2.visibility = View.GONE

                }
            }, { error ->
                bm.webview.visibility = View.GONE
                bm.lot.visibility = View.GONE
                bm.mainView.visibility = View.VISIBLE
                bm.textView2.visibility = View.GONE

            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val responseInfo: MutableMap<String, String> = HashMap()
                responseInfo["Device-UUID"] = fly!!
                return responseInfo
            }
        }
        mainWindows.add(jsMains)
    }

    private fun ErorrsMains(s: String) {

        val errorsW = Volley.newRequestQueue(this)
        val jsErrors = JSONObject()
        jsErrors.put("name", "a_e_w")
        val ErrorsMains = JSONObject()
        ErrorsMains.put("success", true)
        ErrorsMains.put("url", urls1)
        ErrorsMains.put("error", s)

        jsErrors.put("data", ErrorsMains)
        jsErrors.put("created", countryTime())
        var Uri321 = uri2

        val responseAEW = object: JsonObjectRequest(
            Request.Method.POST, Uri321, jsErrors,
            { response ->

            }, { error ->

            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val MutHeads: MutableMap<String, String> = java.util.HashMap()
                MutHeads["Device-UUID"] = fly!!
                return MutHeads
            }
        }
        errorsW.add(responseAEW)
    }

    private fun installInfo( url: String) {

        val VolleyInfo = Volley.newRequestQueue(this)
        val InfoGets = JSONObject()
        InfoGets.put("name", "a_p_f")
        val bodyMains = JSONObject()
        bodyMains.put("success", true)
        bodyMains.put("url", url)
        InfoGets.put("data", bodyMains)
        InfoGets.put("created", countryTime())
        var postLink = uri2

        val urlstoo = object: JsonObjectRequest(
            Request.Method.POST, postLink, InfoGets,
            { response ->
            }, { error ->
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val mains: MutableMap<String, String> = java.util.HashMap()
                mains["Device-UUID"] = fly!!
                return mains
            }
        }
        VolleyInfo.add(urlstoo)
    }

    private fun OpenWebEvents(str: String) {

        val WebOpen = Volley.newRequestQueue(this)
        val WebTelo = JSONObject()
        WebTelo.put("name", "a_o")
        val WebTelo2 = JSONObject()
        WebTelo2.put("success", true)
        WebTelo.put("data", WebTelo2)
        WebTelo.put("created", countryTime())
        var urls = uri2
        val requestJSBody = object: JsonObjectRequest(
            Request.Method.POST, urls, WebTelo,
            { response ->

            }, { error ->

            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val Head: MutableMap<String, String> = HashMap()
                Head["Device-UUID"] = str
                Log.d("IDDEVICE", "$str")
                return Head
            }
        }
        WebOpen.add(requestJSBody)
    }

    private fun countryTime(): Long {
        val times: Date = Calendar.getInstance().time
        return times.time
    }

    private fun fbSDK() {

        FacebookSdk.sdkInitialize(applicationContext)
        FacebookSdk.setAdvertiserIDCollectionEnabled(true)
        FacebookSdk.setApplicationId(cohfigs.getString("facebook"))
        AppLinkData.fetchDeferredAppLinkData(
            this
        ) {
            if(it==null){
                dp = it.toString()
            } else {
                dp = it.getTargetUri().toString()
            }
        }
    }
    override fun onKeyDown(k: Int, e: KeyEvent?): Boolean {
        if ((k == KeyEvent.KEYCODE_BACK) && bm.webview.canGoBack()) {
            bm.webview.goBack()
            return true
        }

        return super.onKeyDown(k, e)
    }
    override fun onBackPressed() {
        if ( bm.webview.isFocused() &&  bm.webview.canGoBack()) {
            bm.webview.goBack()
        } else {
        }
    }
}