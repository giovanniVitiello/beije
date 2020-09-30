package com.example.beije.ui.master

import com.example.beije.Contract
import com.example.beije.response.MonclairObjectResponse
import com.example.beije.utils.exhaustive
import com.example.beije.utils.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class HomeEvent {
    object LoadData : HomeEvent()
}

sealed class HomeState {
    object InProgress : HomeState()
    data class LoadedData(val data: MonclairObjectResponse) : HomeState()
    data class Error(val error: Throwable) : HomeState()
}

class HomeViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<HomeState, HomeEvent>(){

    private var dataSubscription = Disposable.disposed()

    override fun send(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadData -> loadDetailData()
        }.exhaustive
    }

    private fun loadDetailData() {
        if (dataSubscription.isDisposed) {
            post(HomeState.InProgress)
            dataSubscription = contract.getNewsData()
                .observeOn(scheduler)
                .subscribe(
                    { data -> post(HomeState.LoadedData(data)) },
                    { error -> post(HomeState.Error(error)) }
                )
            disposables.add(dataSubscription)
        }
    }

}
