package com.example.beije.ui.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.metiswebdev.veronacalcio.main.Contract
import com.nrc.snr.base.BaseViewModel
import io.reactivex.rxjava3.core.Scheduler

sealed class MasterEvent {
    object LoadNews : MasterEvent()
    object LoadStore : MasterEvent()
}

sealed class MasterState {
    object InProgressNews : MasterState()
    object InProgressStore : MasterState()
    data class LoadedNews(val news: List</*NewsResponse*/>) : MasterState()
    data class Error(val error: Throwable) : MasterState()
}

class MasterViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<MasterState, MasterEvent>(){

    override fun send(event: MasterEvent) {
        TODO("Not yet implemented")
    }

}
