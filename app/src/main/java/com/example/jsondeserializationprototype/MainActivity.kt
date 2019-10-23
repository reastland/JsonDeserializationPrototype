package com.example.jsondeserializationprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.jsondeserializationprototype.gsonmodels.GsonSportsDataApiResponse
import com.example.jsondeserializationprototype.kotlinserializationmodels.KotlinSerializationSportsDataApiResponse
import com.google.gson.Gson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlinx.serialization.*
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import android.R
import android.content.Context
import sun.jvm.hotspot.utilities.IntArray




class MainActivity : AppCompatActivity() {

    @Serializable
    data class Person(val name: String, val species: String, val missing: String = "missing")

    private val kotlinStringBuilder = StringBuilder()
    private val gsonStringBuilder = StringBuilder()

    @UnstableDefault
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        jsonString = assets.open("StatsApiResponse.json").bufferedReader().use { it.readText() }

        go.setOnClickListener {
//            benchmarkKotlin()
            benchmarkGson()
        }
    }

    @UseExperimental(UnstableDefault::class)
    fun benchmarkKotlin() = CoroutineScope(Dispatchers.Main).launch{

        val mediaType = "application/json".toMediaTypeOrNull()
        val retrofit = mediaType?.let {
            Retrofit.Builder()
                .addConverterFactory(Json.nonstrict.asConverterFactory(it))
                .baseUrl(statsApiUrl)
                .build()
        }

        val webService = retrofit?.create(WebService::class.java)

        val t1 = System.currentTimeMillis()

        withContext(Dispatchers.IO) {
            webService?.let {
                val games = fetchGamesUsingKotlin(it)
            }
        }

        val t2 = System.currentTimeMillis()
        val duration = t2 - t1
        kotlinTotalDuration += duration
        val kotlinAverage = kotlinTotalDuration / ++kotlinIterations
        kotlinStringBuilder.append("$duration ms (avg: $kotlinAverage)\n")
        kotlin_results.text = kotlinStringBuilder.toString()

//        benchmarkGson()

        scrollview.fullScroll(View.FOCUS_DOWN)
    }


    @UnstableDefault
    private suspend fun fetchGamesUsingKotlin(webService: WebService): List<KotlinSerializationSportsDataApiResponse.Game> {
        return suspendCancellableCoroutine { cont ->
            webService.getScoreboardForKotlin()
                .enqueue(object : Callback<KotlinSerializationSportsDataApiResponse> {
                    override fun onResponse(
                        call: Call<KotlinSerializationSportsDataApiResponse>,
                        response: Response<KotlinSerializationSportsDataApiResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.dates?.firstOrNull()?.games?.let { cont.resume(it) }
                        } else {
                            cont.cancel(IOException("${response.code()}: ${response.errorBody()}"))
                        }
                    }

                    override fun onFailure(
                        call: Call<KotlinSerializationSportsDataApiResponse>,
                        t: Throwable
                    ) {
                        Log.e(t.toString(), "Unable to get statsapi response")
                        cont.cancel(t)
                    }
                })
        }
    }

    fun benchmarkGson() = CoroutineScope(Dispatchers.Main).launch{
        val mediaType = "application/json".toMediaTypeOrNull()
        val retrofit = mediaType?.let {
            Retrofit.Builder()
                .baseUrl(statsApiUrl)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .client(OkHttpClient().newBuilder().build())
                .build()
        }

        val webService = retrofit?.create(WebService::class.java)
        val t1 = System.currentTimeMillis()
        withContext(Dispatchers.IO) {
            webService?.let {
                val games = fetchGamesUsingGson(it)
            }
        }
        val t2 = System.currentTimeMillis()
        val duration = t2 - t1
        gsonTotalDuration += duration
        val gsonAverage = gsonTotalDuration / ++gsonIterations
        gsonStringBuilder.append("$duration ms (avg: $gsonAverage ms)\n")
        gson_results.text = gsonStringBuilder.toString()

        benchmarkKotlin()

        scrollview.fullScroll(View.FOCUS_DOWN)
    }

    private suspend fun fetchGamesUsingGson(webService: WebService): List<GsonSportsDataApiResponse.Game> {
        return suspendCancellableCoroutine { cont ->
            webService.getScoreboardForGson()
                .enqueue(object : Callback<GsonSportsDataApiResponse> {
                    override fun onResponse(
                        call: Call<GsonSportsDataApiResponse>,
                        response: Response<GsonSportsDataApiResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.dates?.firstOrNull()?.games?.let { cont.resume(it) }
                        } else {
                            cont.cancel(IOException("${response.code()}: ${response.errorBody()}"))
                        }
                    }

                    override fun onFailure(
                        call: Call<GsonSportsDataApiResponse>,
                        t: Throwable
                    ) {
                        Log.e(t.toString(), "Unable to get statsapi response")
                        cont.cancel(t)
                    }
                })
        }
    }




    private suspend fun fetchPerson(webService: WebService): Person {
        return suspendCancellableCoroutine { cont ->
            webService.getPerson()
                .enqueue(object : Callback<Person> {
                    override fun onFailure(call: Call<Person>, t: Throwable) {
                        Log.e(t.toString(), "Unable to get statsapi response")
                        cont.cancel(t)
                    }

                    override fun onResponse(
                        call: Call<Person>,
                        response: Response<Person>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { cont.resume(it) }
                        } else {
                            cont.cancel(IOException("${response.code()}: ${response.errorBody()}"))
                        }
                    }
                })
        }
    }


//    fun benchmarkImmutables() {
//        val target = arrayOfNulls<ImmutablesSportsDataApiResponse>(NUM_ITERATIONS)
//        val t1 = System.currentTimeMillis()
//        val gsonBuilder = GsonBuilder()
//        for (factory in ServiceLoader.load(TypeAdapterFactory::class.java)) {
//            gsonBuilder.registerTypeAdapterFactory(factory)
//        }
//        val gson = gsonBuilder.create()
//        for (i in 0 until NUM_ITERATIONS) {
//            target[i] = gson.fromJson(jsonString, ImmutablesSportsDataApiResponse::class.java)
//        }
//        val t2 = System.currentTimeMillis()
//        val avg = (t2 - t1) / NUM_ITERATIONS
//        stringBuilder.append("Immutables: $avg ms\n")
//    }

    companion object {
//        const val statsApiUrl = "https://statsapi.mlb.com/"
        const val statsApiUrl = "https://s3-us-west-1.amazonaws.com/com.randalleastland/"
        const val date = "09/29/2019"
        const val hydrate = "decisions,probablePitcher,person,stats,team,linescore(matchup,runners,positions),flags,game(content(media(epg),highlights(highlights),limit=5))"
        const val sportId = "1"

        var kotlinIterations = 0
        var gsonIterations = 0
        var kotlinTotalDuration = 0L
        var gsonTotalDuration = 0L
    }
}
