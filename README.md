<h1 align="center">Huawei-SmartAd Header Bidding Adapter Github Documentation</h3>

![Latest Version](https://img.shields.io/badge/latestVersion-1.0-yellow) ![Kotlin](https://img.shields.io/badge/language-kotlin-blue)
![Supported Platforms](https://img.shields.io/badge/Supported_Platforms:-Native_Android_-orange)

# Introduction

The Huawei Header Bidding adapter allows you to connect Huawei Publisher Services in-app bidder SDK with Smart Display SDK.

You will find in this repository the classes you need to connect Huawei Publisher Services in-app bidding and Smart Display SDK, as well as a sample in the [Sample directory](https://github.com/Explore-In-HMS/huawei.ads.smartad.headerbidding/tree/main/app).

## Bidder implementation structure

The Huawei Header Bidding adapter is split into three different classes:

-   SASHuaweiBaseBidderAdapter: this class is an abstract class implementing the `SASBidderAdapter` interface regrouping the code in common for Banner concrete adapter
-   SASHuaweiBannerBidderAdapter: this class implements the `SASBannerBidderAdapter` and is the adapter you should use to load an Huawei banner ad in a SASBannerView, as it provides the needed third party banner rendering capabilities.

## Integrate the Huawei Header Bidding SDK
</h1>

In the **project-level** build.gradle, include Huawei's Maven repository.

```
repositories {
    ...
    maven { url "https://maven.google.com" }
    maven { url 'https://packagecloud.io/smartadserver/android/maven2' }
    maven { url 'https://developer.huawei.com/repo/' }
}

...

allprojects {
    repositories {
        ...
        maven { url "https://maven.google.com" }
        maven { url 'https://packagecloud.io/smartadserver/android/maven2' }
        maven { url 'https://developer.huawei.com/repo/' }
    }
}
```

## Using the Huawei Header Bidding adapter in your app

Request an Huawei ad using HuaweiAds, then:

For banner ads, create an instance of `SASHuaweiBannerBidderAdapter` using the Huawei ad response when the Huawei call is successful, and pass it to the `loadAd()` call on the Smart SASBannerView:

```
huaweiAds.loadAd(
            onSuccess = {
                Log.i(TAG, "Huawei ad request is successful")
                // Huawei returned an ad, wrap it in a SASHuaweiBannerBidderAdapter object and pass it to the Smart ad call
                val bidderAdapter =
                    SASHuaweiBannerBidderAdapter(it!!, this@HeaderBiddingBannerActivity)
                bannerView?.loadAd(adPlacement, bidderAdapter)
            })
```

## **Configuring Network Permissions**
To allow HTTP and HTTPS network requests on devices with targetSdkVersion 28 or later, configure the following information in the AndroidManifest.xml file:

```groovy
<application
    ...
    android:usesCleartextTraffic="true"
    >
    ...
</application>
```

# Version Change History

## 1.0

**_NOTE:_** Huawei Ads Kit SDK didn't support HTML Markup, so ad cannot be displayed. You can access the bid and ad data from Huawei via adapter and you can show this ad yourself in the custom view.

## Licence
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.