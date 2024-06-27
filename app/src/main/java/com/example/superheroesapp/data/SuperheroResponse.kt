package com.example.superheroesapp.data

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

//esta dataclase es para poder devolver una lista
//es similar a lo que sería los getters y setters
data class SuperheroResponse (
    @SerializedName("response") val response: String,
    @SerializedName("results-for") val resultsFor: String?,
    @SerializedName("results") val results: List<Superhero>
){
}

data class Superhero (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: Image,
    @SerializedName("biography") val biography: Biography,
    @SerializedName("appearance") val appearance: Appearance,
    @SerializedName("powerstats") val powerstats: Powerstats
)

data class Powerstats(
    @JsonAdapter(IntegerAdapter::class) @SerializedName("intelligence") val intelligence: Int,
    @JsonAdapter(IntegerAdapter::class)@SerializedName("strength") val strength: Int,
    @JsonAdapter(IntegerAdapter::class)@SerializedName("speed") val speed: Int,
    @JsonAdapter(IntegerAdapter::class)@SerializedName("durability") val durability: Int,
    @JsonAdapter(IntegerAdapter::class)@SerializedName("power") val power: Int,
    @JsonAdapter(IntegerAdapter::class)@SerializedName("combat") val combat: Int

)

data class Image (
    @SerializedName("url") val url: String
)

data class Biography (
    @SerializedName("full-name") val fullName: String,
    @SerializedName("place-of-birth") val placeOfBirth: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("alignment") val alignment: String

)

data class Appearance (
    @SerializedName("gender") val gender: String
)

class IntegerAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter?, value: Int) {
        out?.value(value)
    }

    override fun read(`in`: JsonReader?): Int {
        if (`in` != null) {
            val value: String = `in`.nextString()
            if (value != "null") {
                return value.toInt()
            }
        }
        return 0
    }

}