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

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be use.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()

/**
 * A public interface that exposes the "getBooks" method
 */
interface OpenLibraryApiService {

    /**
     * Returns a list of NewBooks and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("search.json")
    suspend fun getBooks(@Query("author") author : String? = null,
                         @Query("title") title : String? = null,
                         @Query("subject") subject : String? = null): Response<NewBooks>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object OpenLibraryApi{
    val retrofitService : OpenLibraryApiService by lazy {
        retrofit.create(OpenLibraryApiService::class.java)
    }
}