package com.example.superheroesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.example.superheroesapp.R
import com.example.superheroesapp.data.Superhero
import com.example.superheroesapp.data.SuperheroApiService
import com.example.superheroesapp.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    lateinit var superhero: Superhero

    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("SUPERHERO_ID", -1)

        getById(id)

    }

    private fun loadData() {
        binding.nameTextView.text = superhero.name
        binding.fullNameTextView.text = superhero.biography.fullName
        binding.placeOfBirthTextView.text = superhero.biography.placeOfBirth
        binding.publisherTextView.text = superhero.biography.publisher
        binding.publisherTextView.text = superhero.biography.publisher
        binding.alignmentTextView.text = superhero.biography.alignment
        progressBar = findViewById(R.id.progressBar)
        Picasso.get().load(superhero.image.url).into(binding.photoImageView)
    }

    private fun getById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperheroApiService::class.java)
                val result = apiService.getSuperheroById(id)

                superhero = result

                runOnUiThread {
                    loadData()
                    progressBar.visibility = View.GONE
                }
                //Log.i("HTTP", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/7252591128153666/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}