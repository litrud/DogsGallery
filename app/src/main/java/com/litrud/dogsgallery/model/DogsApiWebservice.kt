package com.litrud.dogsgallery.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/* todo
    https://dog.ceo/api/breed/<Fox Terrier>/images/random
    https://dog.ceo/api/breeds/list/all
    https://dog.ceo/api/breeds/image/random
 */

interface DogsApiWebservice {

//    @GET("/breeds/list/all") // todo тут надо получать список пород
//    fun getBreeds() : Call<List<DogPhotoLink>>

    @GET("/breed/{breed}/images")
    fun getPhotosByBreed(@Path("breed") breed : String) : Call<Array<String>>


    companion object Factory {
        private const val URL_BASE = "https://dog.ceo/dog-api"

        fun create() : DogsApiWebservice {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DogsApiWebservice::class.java)
        }
    }
}