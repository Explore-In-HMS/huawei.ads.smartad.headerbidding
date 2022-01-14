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

package com.smartad.adapters.custom.huaweiadsadapter.utils

import android.util.Log
import com.huawei.hms.ads.bidding.data.response.ResponseBody
import com.smartad.adapters.custom.huaweiadsadapter.data.*

fun ResponseBody.map(): HuaweiAdResponse {
    try {
        val adResponse = HuaweiAdResponse()

        adResponse.clientAdRequestId = this.clientAdRequestId
        adResponse.ctrlSwitchs = this.ctrlSwitchs
        adResponse.dsp1cost = this.dsp1cost
        adResponse.dspcost = this.dspcost
        adResponse.sig = this.sig
        adResponse.retcode = this.retcode
        adResponse.totalCacheSize = this.totalCacheSize

        //region MultiAd
        if (this.multiad == null)
            adResponse.multiad = null
        else {
            val multiAds = arrayListOf<Multiad>()
            for (iMultiAd in this.multiad!!) {
                val multiAd = Multiad()
                multiAd.adtype = iMultiAd.adtype
                multiAd.retcode30 = iMultiAd.retcode30
                multiAd.slotid = iMultiAd.slotid

                //region ConfigMap
                if (iMultiAd.configMap == null) {
                    multiAd.configMap = null
                } else {
                    val configMap = ConfigMap()
                    configMap.insreCacheAdEnable = iMultiAd.configMap!!.insreCacheAdEnable
                    multiAd.configMap = configMap
                }
                //endregion

                //region Content
                if (iMultiAd.content == null) {
                    multiAd.content = null
                } else {
                    val contentList = iMultiAd.content
                    val contents = arrayListOf<Content>()
                    for (iContent in contentList!!) {
                        val content = Content()
                        content.contentid = iContent.contentid
                        content.creativetype = iContent.creativetype
                        content.ctrlSwitchs = iContent.ctrlSwitchs
                        content.cur = iContent.cur
                        content.dspExt = iContent.dspExt
                        content.endtime = iContent.endtime
                        content.interactiontype = iContent.interactiontype
                        content.price = iContent.price
                        content.starttime = iContent.starttime
                        content.taskid = iContent.taskid
                        content.webConfig = iContent.webConfig
                        content.whyThisAd = iContent.whyThisAd

                        //region ClickActionList
                        if (iContent.clickActionList == null) {
                            content.clickActionList = null
                        } else {
                            val clickActions = arrayListOf<Int>()
                            for (item in iContent.clickActionList!!) {
                                clickActions.add(item)
                            }
                            content.clickActionList = clickActions
                        }
                        //endregion

                        //region FilterList
                        if (iContent.filterList == null) {
                            content.filterList = null
                        } else {
                            val filters = arrayListOf<Int>()
                            for (item in iContent.filterList!!) {
                                filters.add(item)
                            }
                            content.filterList = filters
                        }
                        //endregion

                        //region MetaData
                        if (iContent.metaData == null) {
                            content.metaData = null
                        } else {
                            val iMetaData = iContent.metaData!!
                            val metaData = MetaData()
                            metaData.adSign = iMetaData.adSign
                            metaData.appPromotionChannel = iMetaData.appPromotionChannel
                            metaData.clickUrl = iMetaData.clickUrl
                            metaData.cta = iMetaData.cta
                            metaData.label = iMetaData.label
                            metaData.landingPageType = iMetaData.landingPageType
                            metaData.title = iMetaData.title

                            //region ImageInfo
                            if (iMetaData.imageInfo == null) {
                                metaData.imageInfo = null
                            } else {
                                val imageInfos = arrayListOf<ImageInfo>()
                                for (item in iMetaData.imageInfo!!) {
                                    val imageInfo = ImageInfo()
                                    imageInfo.checkSha256Flag = item.checkSha256Flag
                                    imageInfo.height = item.height
                                    imageInfo.imageType = item.imageType
                                    imageInfo.sha256 = item.sha256
                                    imageInfo.url = item.url
                                    imageInfo.width = item.width

                                    imageInfos.add(imageInfo)
                                }
                                metaData.imageInfo = imageInfos
                            }
                            //endregion

                            content.metaData = metaData
                        }
                        //endregion

                        //region Monitor
                        if (iContent.monitor == null) {
                            content.monitor = null
                        } else {
                            val monitors = arrayListOf<Monitor>()
                            for (item in iContent.monitor!!) {
                                val monitor = Monitor()
                                monitor.eventType = item.eventType
                                monitor.url = item.url

                                monitors.add(monitor)
                            }
                            content.monitor = monitors
                        }
                        //endregion

                        //region ParamFromServer
                        if (iContent.paramfromserver == null) {
                            content.paramfromserver = null
                        } else {
                            val paramFromServer = Paramfromserver()
                            paramFromServer.a = iContent.paramfromserver!!.a
                            paramFromServer.sig = iContent.paramfromserver!!.sig
                            paramFromServer.t = iContent.paramfromserver!!.t
                            content.paramfromserver = paramFromServer
                        }
                        //endregion

                        //region XrFlag
                        if (iContent.xrFlag == null) {
                            content.xrFlag = null
                        } else {
                            val xrFlag = XrFlag()
                            xrFlag.temp = iContent.xrFlag!!.temp
                            content.xrFlag = xrFlag
                        }
                        //endregion
                        contents.add(content)
                    }
                    multiAd.content = contents
                }
                //endregion
                multiAds.add(multiAd)
            }
            adResponse.multiad = multiAds
        }
        //endregion

        //region NoReportAdTypeEvent
        if (this.noReportAdTypeEventList == null) {
            adResponse.noReportAdTypeEventList = null
        } else {
            val events = arrayListOf<NoReportAdTypeEvent>()
            for (iEvent in this.noReportAdTypeEventList!!) {
                val event = NoReportAdTypeEvent()
                event.adType = iEvent.adType

                //region EventTypeList
                if (iEvent.eventTypeList == null) {
                    event.eventTypeList = null
                } else {
                    val types = arrayListOf<String>()
                    for (item in iEvent.eventTypeList!!) {
                        types.add(item)
                    }
                    event.eventTypeList = types
                }
                //endregion

                events.add(event)
            }
            adResponse.noReportAdTypeEventList = events
        }
        //endregion

        //region Reason
        if (this.reason == null) {
            adResponse.reason = null
        } else {
            adResponse.reason = this.reason
        }
        //endregion

        return adResponse
    } catch (e: Exception) {
        Log.d(Constants.SAS_TAG, "Mapping error: " + e.message)
        return HuaweiAdResponse()
    }
}