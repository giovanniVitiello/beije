package com.metiswebdev.veronacalcio.main.network

import android.content.res.Resources
import com.example.beije.R
import com.metiswebdev.veronacalcio.main.network.response.DetailNewsResponse
import com.metiswebdev.veronacalcio.main.network.response.NewsResponse
import com.metiswebdev.veronacalcio.main.network.response.StoreResponse
import com.google.gson.Gson
import com.metiswebdev.Hellas.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

@SuppressWarnings("TooManyFunctions")
class Backend(
    gson: Gson,
    networkThread: Scheduler = Schedulers.io(),
    resources: Resources
) {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val privateOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(networkThread))
        .baseUrl(resources.getString(/*R.string.base_url*/))
        .client(privateOkHttpClient)
        .build()
        .create(ListingApi::class.java)

    fun getNewsData(): Single<List<NewsResponse>> = api.getNewsData()

    fun getDetailNewsData(id: Int): Single<DetailNewsResponse> = api.getDetailNewsData(id)

    private interface ListingApi {

        @GET("mobile/news")
        fun getNewsData(): Single<List<NewsResponse>>

        @GET("mobile/news/{id}")
        fun getDetailNewsData(@Path("id") id: Int): Single<DetailNewsResponse>
    }
}
