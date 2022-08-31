package com.hfad.bookcollectionmanager.network

import com.hfad.bookcollectionmanager.data.NewBooks
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://openlibrary.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()

interface OpenLibraryApiService {

    @GET("search.json")
    suspend fun getBooks(@Query("author") author : String? = null,
                         @Query("title") title : String? = null,
                         @Query("subject") subject : String? = null): Response<NewBooks>

}

//Object Declaration
object OpenLibraryApi{
    val retrofitService : OpenLibraryApiService by lazy {
        retrofit.create(OpenLibraryApiService::class.java)
    }
}