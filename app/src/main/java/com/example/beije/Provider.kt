package com.example.beije

import io.reactivex.rxjava3.core.Single

interface Contract {
    fun getData(): Single<List<MonclairResponse>>
}

class Provider(private val backend: Backend) : Contract {
    override fun getData(): Single<List<MonclairResponse>> {
        return backend.getData()
    }

}
