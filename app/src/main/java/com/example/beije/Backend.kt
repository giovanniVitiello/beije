package com.example.beije

import android.content.res.Resources
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

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

    fun getNewsData(): Single<List<MonclairResponse>> = api.getNewsData()

    fun getDetailNewsData(id: Int): Single<MonclairResponse> = api.getDetailNewsData(id)

    private interface ListingApi {

        @GET("")
        fun getNewsData(): Single<List<MonclairResponse>>

}
