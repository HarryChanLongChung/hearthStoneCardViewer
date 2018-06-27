# HearthStoneCardViewer - Kotlin
This is made to try out :
  - okhttp3 & parse adapter by moshi with Kotlin
  - ConstraintLayout
  - Splash Screen implementation


## Demo
Basic :
![](https://github.com/HarryChanLongChung/hearthStoneCardViewer/blob/master/demoGif_startup.gif =100x)

Searching functionality :
![](https://github.com/HarryChanLongChung/hearthStoneCardViewer/blob/master/demoGif_search.gif =100x)

## Code
Using moshi with data class allows me to write a least amount of code to parse the JSON response from this [API](https://market.mashape.com/omgvamp/hearthstone#all-cards)

Data Class :
```kotlin
class Models {
    data class MpApiJsonResponse(
            @Json(name = "Basic") val Cards: List<Card> = listOf()
    )

    data class Card(
            val cardId: String = "",
            val name: String = "",
            val img: String = "",
            val type: String = "",
            val playerClass: String = ""
    )
}
```

Conversion using moshi :
```kotlin
val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
val responseAdapter = moshi.adapter(Models.MpApiJsonResponse::class.java)

...
...
...

// when the request is successful
override fun onResponse(call: Call, response: Response) {
    if (!response.isSuccessful) callback.onFail(IOException("Unexpected code $response"))
    callback.onSuccess(responseAdapter.fromJson(response.body()!!.source()))
}

```

## Libraries
(THEY ARE REALLY AWESOME !!!)
  - [Okhttp3](https://github.com/square/okhttp)
  - [Moshi](https://github.com/square/moshi)
  - [Picasso](https://github.com/square/picasso)