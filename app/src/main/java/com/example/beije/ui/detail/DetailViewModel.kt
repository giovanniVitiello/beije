package com.example.beije.ui.detail

import com.example.beije.Contract
import com.example.beije.utils.exhaustive
import com.nrc.snr.base.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class DetailEvent {
    data class Load(val id: Int) : DetailEvent()
}

sealed class DetailState {
    object InProgress : DetailState()
    data class Loaded(val detail: MonclairResponse) : DetailState()
    data class Error(val error: Throwable) : DetailState()
}

class DetailViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<DetailState, DetailEvent>() {
    private var subscription = Disposable.disposed()

    override fun send(event: DetailEvent) {
        when (event) {
            is DetailEvent.Load -> loadDetailData(event.id)
        }.exhaustive
    }

    private fun loadDetailData(id: Int) {
        if (subscription.isDisposed) {
            post(DetailState.InProgress)
            subscription = contract.getData()
                .observeOn(scheduler)
                .subscribe(
                    { detail -> post(DetailState.Loaded(detail)) },
                    { error -> post(DetailState.Error(error)) }
                )
            disposables.add(subscription)
        }
    }
}
