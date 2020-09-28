package com.metiswebdev.veronacalcio.main

import com.metiswebdev.veronacalcio.main.network.Backend
import io.reactivex.rxjava3.core.Single

interface Contract {
    fun getNewsData(): Single<List<NewsResponse>>
    fun getDetailNewsData(id: Int): Single<DetailNewsResponse>
}

class Provider(private val backend: Backend) : Contract {
    override fun getNewsData(): Single<List<NewsResponse>> {
        return backend.getNewsData()
    }

    override fun getDetailNewsData(id: Int): Single<DetailNewsResponse> {
        return backend.getDetailNewsData(id)
    }

}
