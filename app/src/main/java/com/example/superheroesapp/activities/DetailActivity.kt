package com.example.superheroesapp.activities

import android.annotation.SuppressLint
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


import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate




class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    lateinit var superhero: Superhero

    lateinit var progressBar: ProgressBar

    lateinit var gender: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("SUPERHERO_ID", -1)

        getById(id)

    }

    @SuppressLint("ResourceAsColor")
    private fun loadData() {
        binding.nameTextView.text = superhero.name
        binding.fullNameTextView.text = superhero.biography.fullName
        binding.placeOfBirthTextView.text = superhero.biography.placeOfBirth
        binding.publisherTextView.text = superhero.biography.publisher
        binding.publisherTextView.text = superhero.biography.publisher
        binding.alignmentTextView.text = superhero.biography.alignment
        progressBar = findViewById(R.id.progressBar)
        Picasso.get().load(superhero.image.url).into(binding.photoImageView)
        binding.frameLayout.setBackgroundColor(getColor(R.color.blue))

        pintarGraficos()

        gender = superhero.appearance.gender
        if (gender.toLowerCase() == "female") {
            binding.frameLayout.setBackgroundColor(getColor(R.color.pink))
        }



    }

    fun pintarGraficos()
    {
        //Creamos las entradas para el gráfico
        val chart = findViewById<BarChart>(R.id.barChart)
        val entries = ArrayList<BarEntry>()
         entries.add(BarEntry(0f, superhero.powerstats.intelligence.toFloat()))
         entries.add(BarEntry(1f, superhero.powerstats.strength.toFloat()))
         entries.add(BarEntry(2f, superhero.powerstats.speed.toFloat()))
         entries.add(BarEntry(3f, superhero.powerstats.durability.toFloat()))
         entries.add(BarEntry(4f, superhero.powerstats.power.toFloat()))
         entries.add(BarEntry(5f, superhero.powerstats.combat.toFloat()))

        // Creamos el conjunto de datos y lo añadimos al gráfico
         val barDataSet = BarDataSet(entries, "Powerstats")
         barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
         val data = BarData(barDataSet)

        // Añadimos las etiquetas al eje X
        val xAxisLabels = listOf("Intelligence", "Strength", "Speed", "Durability", "Power", "Combat")
        val xAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)

        binding.barChart.data = data
        chart.description.isEnabled = false
        binding.barChart.invalidate() //para refrescar el grafico

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