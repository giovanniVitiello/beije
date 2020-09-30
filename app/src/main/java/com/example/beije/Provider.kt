package com.example.beije

import com.example.beije.response.MonclairObjectResponse
import io.reactivex.rxjava3.core.Single

interface Contract {
    fun getNewsData(): Single<MonclairObjectResponse>
}

class Provider(private val backend: Backend) : Contract {
    override fun getNewsData(): Single<MonclairObjectResponse> = backend.getData()
}
