package com.example.superheroesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.superheroesapp.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://superheroapi.com/api/2ba7f57e36c5a4d961aa9ee05639d6d6/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}