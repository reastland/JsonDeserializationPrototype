package com.example.jsondeserializationprototype

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jsondeserializationprototype.gsonmodels.GsonSportsDataApiResponse
import com.google.gson.Gson

import org.joda.time.DateTime

class GsonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonString = assets.open("StatsApiResponse.json").bufferedReader().use { it.readText() }
        val start = DateTime.now()
        val serialized = Gson().fromJson(jsonString, GsonSportsDataApiResponse::class.java)
        val finish = DateTime.now()
        val elapsedTime = finish.millis - start.millis

        val returnIntent = Intent()
        returnIntent.putExtra("library", "gson")
        returnIntent.putExtra("elapsedTime", elapsedTime)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

}
