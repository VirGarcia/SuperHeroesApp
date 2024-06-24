package com.example.superheroesapp.data

import retrofit2.http.GET
import retrofit2.http.Path


//en retrofit hay que utilizar interfaces
//suspend significa que se ejectua en segundo plano
interface SuperheroApiService {
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name")query: String) : SuperheroResponse
}