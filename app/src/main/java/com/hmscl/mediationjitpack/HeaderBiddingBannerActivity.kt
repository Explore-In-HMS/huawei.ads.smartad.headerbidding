/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hmscl.mediationjitpack

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.smartad.adapters.custom.huaweiadsadapter.HuaweiAds
import com.smartad.adapters.custom.huaweiadsadapter.SASHuaweiBannerBidderAdapter
import com.smartad.adapters.custom.huaweiadsadapter.utils.AdType
import com.smartad.adapters.custom.huaweiadsadapter.utils.NetworkType
import com.smartadserver.android.library.model.SASAdElement
import com.smartadserver.android.library.model.SASAdPlacement
import com.smartadserver.android.library.ui.SASBannerView
import com.smartadserver.android.library.ui.SASRotatingImageLoader
import com.smartadserver.android.library.util.SASConfiguration

class HeaderBiddingBannerActivity : AppCompatActivity() {

    private val TAG = HeaderBiddingBannerActivity::class.java.simpleName

    /**
     * Ad Constants
     */
    private val SITE_ID: Long = 351387
    private val PAGE_ID = "1231281"
    private val FORMAT_ID: Long = 90738
    private val TARGET = ""

    /*****************************************
     * Members declarations
     */
    // Banner view (as declared in the activity_banner_hb.xml layout file, in res/layout)
    var bannerView: SASBannerView? = null


    /**
     * performs Activity initialization after creation
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_bidding_banner)

        // First of all, configure the SDK
        try {
            SASConfiguration.getSharedInstance().configure(applicationContext, SITE_ID.toInt())
        } catch (e: Exception) {
            Log.w("Sample", "Smart SDK configuration failed: " + e.message)
        }

        // Enable output to Android Logcat (optional)
        SASConfiguration.getSharedInstance().isLoggingEnabled = true

        //Set Title
        setTitle(R.string.title_activity_banner_hb)
        /**
         * now perform Ad related code here
         */


        // Enable debugging in Webview if available (optional)
        WebView.setWebContentsDebuggingEnabled(true)

        // Initialize SASBannerView
        initBannerView()

        // Create button to manually refresh the ad
        val refreshBannerButton = findViewById<Button>(R.id.reloadButton);
        refreshBannerButton.setOnClickListener {
            loadBannerAd()
        }

        // Load Banner ad
        loadBannerAd()
    }

    /**
     * Overriden to clean up SASAdView instances. This must be done to avoid IntentReceiver leak.
     */
    override fun onDestroy() {
        bannerView?.onDestroy()
        super.onDestroy()
    }

    /**
     * Initialize the SASBannerView instance of this Activity
     */
    private fun initBannerView() {
        // Fetch the SASBannerView inflated from the main.xml layout file
        bannerView = findViewById<View>(R.id.header_bidding_banner) as SASBannerView

        // Add a loader view on the banner. This view covers the banner placement, to indicate progress, whenever the banner is loading an ad.
        // This is optional
        val loader: View = SASRotatingImageLoader(this)
        loader.setBackgroundColor(resources.getColor(R.color.white))
        bannerView!!.setLoaderView(loader)

        // Set the listener used when load an ad on the banner
        bannerView!!.bannerListener = object : SASBannerView.BannerListener {
            override fun onBannerAdLoaded(
                sasBannerView: SASBannerView?,
                sasAdElement: SASAdElement?
            ) {
                Log.i(TAG, "Banner loading completed.")
            }

            override fun onBannerAdFailedToLoad(sasBannerView: SASBannerView?, e: Exception) {
                Log.i(TAG, "Banner loading failed: " + e.message)
            }

            override fun onBannerAdClicked(sasBannerView: SASBannerView?) {
                Log.i(TAG, "Banner clicked.")
            }

            override fun onBannerAdExpanded(sasBannerView: SASBannerView?) {
                Log.i(TAG, "Banner expanded.")
            }

            override fun onBannerAdCollapsed(sasBannerView: SASBannerView?) {
                Log.i(TAG, "Banner collapsed.")
            }

            override fun onBannerAdResized(sasBannerView: SASBannerView?) {
                Log.i(TAG, "Banner resized.")
            }

            override fun onBannerAdClosed(sasBannerView: SASBannerView?) {
                Log.i(TAG, "Banner closed.")
            }

            override fun onBannerAdVideoEvent(sasBannerView: SASBannerView?, i: Int) {
                Log.i(TAG, "Banner video event: $i")
            }
        }
    }

    /**
     * Loads an ad on the banner
     */
    private fun loadBannerAd() {

        // Create Smart ad placement
        val adPlacement = SASAdPlacement(SITE_ID, PAGE_ID, FORMAT_ID, TARGET)

        // Create an ad size object and pass it to the ad request object
        val huaweiAds = HuaweiAds(this)
        huaweiAds.prepareAccount(
            "316673685098355328",
            "1641474494000",
            "1739b817b131431798d4dc5b2b1d92c70709c37d333db15b4ebbb73ddbe7117c",
            5
        )

        huaweiAds.prepareAdParams(
            AdType.BANNER_AD,
            170,
            2,
            6,
            "k9qa0v7zr4",
            false,
            1080
        )

        //region Request Id
        huaweiAds.prepareClientAdRequestId("e7d371fe-cc24-49fb-a56c-7e83525fa51d")
        //endregion

        //region Device
        huaweiAds.prepareDevice(
            "FRO-LGRP2-OVS 5.0.1.110(log)",
            "WEUROPE",
            "60100305"
        )

        huaweiAds.prepareNetwork(NetworkType.UNKNOWN)


        huaweiAds.loadAd(
            onSuccess = {
                Log.i(TAG, "Huawei ad request is successful")
                // Huawei returned an ad, wrap it in a SASHuaweiBannerBidderAdapter object and pass it to the Smart ad call
                val bidderAdapter =
                    SASHuaweiBannerBidderAdapter(it!!, this@HeaderBiddingBannerActivity)
                bannerView?.loadAd(adPlacement, bidderAdapter)
            },
            onFail = {
                Log.e(TAG, "Huawei ad request failed with error: " + it?.message)
                // fallback: Smart call without Huawei header bidding object
                bannerView?.loadAd(adPlacement)
            })
    }
}