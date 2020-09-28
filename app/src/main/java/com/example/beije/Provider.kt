package com.example.beije

import com.example.beije.response.MonclairObjectResponse
import io.reactivex.rxjava3.core.Single

interface Contract {
    fun getData(): Single<List<MonclairObjectResponse>>
}

class Provider(private val backend: Backend) : Contract {
    override fun getData(): Single<List<MonclairObjectResponse>> {
        return backend.getData()
    }

}
