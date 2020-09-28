package com.example.beije.ui.master

import com.example.beije.Contract
import com.example.beije.ui.detail.DetailState
import com.example.beije.utils.exhaustive
import com.nrc.snr.base.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class MasterEvent {
    object LoadData : MasterEvent()
}

sealed class MasterState {
    object InProgress : MasterState()
    data class LoadedData(val news: List<MonclairResponse>) : MasterState()
    data class Error(val error: Throwable) : MasterState()
}

class MasterViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<MasterState, MasterEvent>(){

    private var dataSubscription = Disposable.disposed()

    override fun send(event: MasterEvent) {
        when (event) {
            is MasterEvent.LoadData -> loadDetailData()
        }.exhaustive
    }

    private fun loadDetailData() {
        if (dataSubscription.isDisposed) {
            post(MasterState.InProgress)
            dataSubscription = contract.getData()
                .observeOn(scheduler)
                .subscribe(
                    { data -> post(MasterState.LoadedData(data)) },
                    { error -> post(MasterState.Error(error)) }
                )
            disposables.add(dataSubscription)
        }
    }

}
