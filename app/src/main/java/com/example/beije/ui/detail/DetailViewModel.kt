package com.example.beije.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metiswebdev.veronacalcio.main.Contract
import com.nrc.snr.base.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class DetailEvent {
    data class LoadDetailNews(val id: Int) : DetailEvent()
}

sealed class DetailState {
    object InProgressNews : DetailState()
    data class LoadedNews(val newsDetail: DetailNewsResponse) : DetailState()
    data class Error(val error: Throwable) : DetailState()
}

class DetailViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<DetailState, DetailEvent>() {
    private var newsSubscription = Disposable.disposed()

    override fun send(event: DetailEvent) {
        when (event) {
            is DetailEvent.LoadDetailNews -> loadDetailNews(event.id)
        }.exhaustive
    }

    private fun loadDetailNews(id: Int) {
        if (newsSubscription.isDisposed) {
            post(DetailState.InProgressNews)
            newsSubscription = contract.getDetailNewsData(id)
                .observeOn(scheduler)
                .subscribe(
                    { newsDetail -> post(DetailState.LoadedNews(newsDetail)) },
                    { error -> post(DetailState.Error(error)) }
                )
            disposables.add(newsSubscription)
        }
    }
}
