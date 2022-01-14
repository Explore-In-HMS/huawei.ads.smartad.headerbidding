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

package com.smartad.adapters.custom.huaweiadsadapter

import android.content.Context
import android.util.Log
import com.huawei.hms.ads.bidding.Bidding
import com.smartad.adapters.custom.huaweiadsadapter.data.HuaweiAdResponse
import com.smartad.adapters.custom.huaweiadsadapter.utils.Constants.SAS_TAG
import com.smartad.adapters.custom.huaweiadsadapter.utils.map

class HuaweiAds(context: Context) {

    private val bidding = Bidding(context)

    /**
     * Initialize Huawei Ads account
     */
    fun prepareAccount(username: String, nonce: String, response: String, keyId: Int) {
        bidding.initializeAccount(username, nonce, response, keyId)
        bidding.prepareApp()
        bidding.prepareVersion()
    }

    /**
     * Initialize Ad params
     */
    fun prepareAdParams(
        adType: Int,
        height: Int,
        isSmart: Int,
        maxCount: Int,
        slotId: String,
        test: Boolean,
        width: Int
    ) {
        bidding.prepareMultiSlot(adType, height, isSmart, maxCount, slotId, test, width)
    }

    /**
     * Prepare Client Ad Request Id
     */
    fun prepareClientAdRequestId(clientAdRequestId: String) {
        bidding.prepareClientAdRequestId(clientAdRequestId)
    }

    /**
     * Initialize Device Info
     */
    fun prepareDevice(
        buildVersion: String,
        vendorCountry: String,
        verCodeOfHms: String
    ) {
        bidding.prepareDevice(buildVersion, vendorCountry, verCodeOfHms)
    }

    /**
     * Initialize Network info
     * [NetworkType]
     */
    fun prepareNetwork(type: Int) {
        bidding.prepareNetwork(type, 2)
    }

    /**
     * Load Huawei Ad
     */
    fun loadAd(
        onSuccess: ((body: HuaweiAdResponse?) -> Unit)? = null,
        onFail: ((e: Exception?) -> Unit)? = null
    ) {
        bidding.bid(
            onSuccess = {
                try {
                    if (it != null) {
                        val response = it.map()
                        onSuccess?.invoke(response)
                    } else
                        onFail?.invoke(null)

                } catch (e: Exception) {
                    onFail?.invoke(e)
                }
            },
            onFail = {
                onFail?.invoke(null)
            })
    }
}