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

import com.smartad.adapters.custom.huaweiadsadapter.data.HuaweiAdResponse
import com.smartadserver.android.library.thirdpartybidding.SASBidderAdapter

open class SASHuaweiBaseBidderAdapter(adResponse: HuaweiAdResponse) : SASBidderAdapter {

    //region SASBidderAdapter variables

    // the name of the winning SSP
    private lateinit var winningSSPName: String

    // the winning creative ID
    private lateinit var winningCreativeId: String

    // the winning deal ID
    private lateinit var dealId: String

    // the keyword representing the pricepoint
    private var keyword: String? = null

    private var currency: String? = null
    private var price: Double? = null

    //endregion

    /**
     * Creates a [SASHuaweiBaseBidderAdapter] from Huawei ad response
     */
    init {
        // If no ads in the adResponse, return nil, there is no winning ad.
        if (adResponse.multiad?.size == 0) {
            throw IllegalArgumentException("No ad found in Huawei's response.")
        }

        //region store ad response attributes
        // Using 'Huawei' as SSP name since Huawei will never return the real name of the winning ssp
        winningSSPName = "Huawei"
        winningCreativeId = ""

        dealId = adResponse.clientAdRequestId.toString()
        currency = adResponse.multiad?.get(0)?.content?.get(0)?.cur
        price = adResponse.multiad?.get(0)?.content?.get(0)?.price
        //endregion

        // Keyword
        val customParams = adResponse.multiad
        if (customParams != null && customParams.isNotEmpty()) {
            keyword = "huaweislots=" + customParams[0].slotid
        } else {
            throw IllegalArgumentException("No price point found for Huawei's response.")
        }
    }

    override fun getAdapterName(): String {
        return "Huawei"
    }

    override fun getCompetitionType(): SASBidderAdapter.CompetitionType {
        return SASBidderAdapter.CompetitionType.Keyword
    }

    override fun getRenderingType(): SASBidderAdapter.RenderingType {
        return SASBidderAdapter.RenderingType.Mediation
    }

    /**
     * WINNING CREATIVE INFORMATION
     */
    override fun getWinningSSPName(): String {
        return winningSSPName
    }

    override fun getWinningCreativeId(): String {
        return winningCreativeId
    }

    override fun getPrice(): Double {
        return price ?: 0.0
    }

    override fun getCurrency(): String? {
        return currency
    }

    override fun getKeyword(): String? {
        return keyword
    }

    override fun getDealId(): String? {
        return dealId
    }

    /**
     * WIN NOTIFICATION CALLBACK
     */
    override fun primarySDKLostBidCompetition() {
        // Nothing to do here unless you want to log some information.
    }


    /**
     * SMART DISPLAY SDK CREATIVE RENDERING
     */
    override fun getBidderWinningAdMarkup(): String? {
        return null
    }

    override fun primarySDKDisplayedBidderAd() {
        // Nothing to do here this method will not be called on Mediation rendering type.
    }

    override fun primarySDKClickedBidderAd() {
        // Nothing to do here this method will not be called on Mediation rendering type.
    }

    /**
     * THIRD PARTY CREATIVE RENDERING
     */
    override fun primarySDKRequestedThirdPartyRendering() {
        // Nothing to do here this method will not be called on Mediation rendering type.
    }

}