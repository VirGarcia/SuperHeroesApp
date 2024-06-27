package com.example.superheroesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superheroesapp.R
import com.example.superheroesapp.adapters.SuperheroAdapter
import com.example.superheroesapp.data.Superhero
import com.example.superheroesapp.data.SuperheroApiService
import com.example.superheroesapp.data.SuperheroResponse
import com.example.superheroesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: SuperheroAdapter

    lateinit var superheroList: List<Superhero>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        superheroList = emptyList()
        adapter = SuperheroAdapter(superheroList) { position ->
            navigateToDetail(superheroList[position])
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchByName("bat")

    }
    /*override fun onResume() {
        super.onResume()
        adapter.updateData(emptyList())
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchByName(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    private fun navigateToDetail(superhero: Superhero) {
        //Toast.makeText(this, superhero.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("SUPERHERO_ID", superhero.id)
        startActivity(intent)
    }

    private fun searchByName(query: String){
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperheroApiService::class.java)
                val result = apiService.findSuperheroesByName(query)

                runOnUiThread {
                    superheroList = result.results
                    adapter.updateData(superheroList)
                }
                //Log.i("HTTP", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        /*val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/
        return Retrofit.Builder()
            //en postman para probar esta url, despues de la barra hay que meterle el método, y eso y viene en la api
            //en este ejemplo sería: https://superheroapi.com/api/7252591128153666/search/batman
            //ponemos search/el nombre de quién queremos buscar

            .baseUrl("https://superheroapi.com/api/2ba7f57e36c5a4d961aa9ee05639d6d6/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
