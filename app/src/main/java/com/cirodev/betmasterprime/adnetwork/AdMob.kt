package com.cirodev.betmasterprime.adnetwork


import android.content.Context
import android.util.Log
import com.cirodev.betmasterprime.R
import com.cirodev.util.findActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback



var mInterstitialAd : InterstitialAd? = null


// cargamos el interstitials ad

fun loadInterstitial(context: Context) {
    InterstitialAd.load(
        context,
        context.getString(R.string.ad_id_interstitial),
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                Log.d("GetDataSoccerTips", adError.message)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                Log.d("GetDataSoccerTips", "Ad was loaded")
            }
        }
    )
}

// add the interstitial ad callbacks

fun addInterstitialCallbacks(context: Context) {
    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            Log.d("GetDataSoccerTips", "Ad failed to show.")
        }

        override fun onAdShowedFullScreenContent() {
            mInterstitialAd = null
            Log.d("GetDataSoccerTips", "Ad showed fullscreen content")
            loadInterstitial(context)
        }

        override fun onAdDismissedFullScreenContent() {

            Log.d("GetDataSoccerTips", "Ad was dismissed.")

        }
    }
}

// show the interstitial ad
fun showInterstitial(context: Context){
    val activity = context.findActivity()

    if (mInterstitialAd != null){
        mInterstitialAd?.show(activity!!)
    } else{
        Log.d("GetDataSoccerTips", "The interstitial ad wasn't ready yet.")

    }
}