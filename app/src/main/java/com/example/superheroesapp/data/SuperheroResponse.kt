package com.example.superheroesapp.data

import com.google.gson.annotations.SerializedName

//esta dataclase es para poder devolver una lista
//es similar a lo que sería los getters y setters
data class SuperheroResponse (
    @SerializedName("response") val response: String,
    @SerializedName("results-for") val resultsFor: String?,
    @SerializedName("results") val results: List<Superhero>
){
}

data class Superhero (
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: Image
) {
}

data class Image (
    @SerializedName("url") val url: String
)