package com.example.beije.ui.detail

import com.example.beije.Contract
import com.example.beije.utils.exhaustive
import com.nrc.snr.base.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class DetailEvent {
    data class LoadDetailNews(val id: Int) : DetailEvent()
}

sealed class DetailState {
    object InProgressNews : DetailState()
    data class LoadedNews(val newsDetail: MonclairResponse) : DetailState()
    data class Error(val error: Throwable) : DetailState()
}

class DetailViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<DetailState, DetailEvent>() {
    private var newsSubscription = Disposable.disposed()

    override fun send(event: DetailEvent) {
        when (event) {
            is DetailEvent.LoadDetailNews -> loadDetailData(event.id)
        }.exhaustive
    }

    private fun loadDetailData(id: Int) {
        if (newsSubscription.isDisposed) {
            post(DetailState.InProgressNews)
            newsSubscription = contract.getData()
                .observeOn(scheduler)
                .subscribe(
                    { newsDetail -> post(DetailState.LoadedNews(newsDetail)) },
                    { error -> post(DetailState.Error(error)) }
                )
            disposables.add(newsSubscription)
        }
    }
}
