package com.example.beije.ui.detail

import com.example.beije.Contract
import com.example.beije.response.MonclairObjectResponse
import com.example.beije.utils.exhaustive
import com.nrc.snr.base.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

sealed class DetailEvent {
    object Load : DetailEvent()
}

sealed class DetailState {
    object InProgress : DetailState()
    object Loaded : DetailState()
    data class Error(val error: Throwable) : DetailState()
}

class DetailViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<DetailState, DetailEvent>() {
    private var subscription = Disposable.disposed()

    override fun send(event: DetailEvent) {
        when (event) {
            is DetailEvent.Load -> Timber.i("prova")
        }.exhaustive
    }

}
