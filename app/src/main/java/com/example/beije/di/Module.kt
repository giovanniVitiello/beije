package com.example.beije.di

import com.example.beije.ui.detail.DetailViewModel
import com.example.beije.ui.master.MasterViewModel
import com.example.beije.Contract
import com.example.beije.Provider
import com.example.beije.Backend
import com.google.gson.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val androidComponents = module {
    single { androidContext().resources }
}

val appComponents = module {
    single { createGson() }
    single { Provider(backend = get()) } bind Contract::class
    single { Backend(gson = get(), resources = get()) }
}

val viewModels = module {
    viewModel {
        MasterViewModel(
            scheduler = AndroidSchedulers.mainThread(),
            contract = get()
        )
    }
    viewModel {
        DetailViewModel(
            scheduler = AndroidSchedulers.mainThread(),
            contract = get()
        )
    }
}

private fun createGson(): Gson = GsonBuilder().create()
