package maw.mobet.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.HistoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class AccountViewModel : ViewModel() {
    private val _list = MutableLiveData<List<HistoryItem>>().apply {
        loadData()
    }

    val list: LiveData<List<HistoryItem>>
        get() = _list

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.historyList()
        dataCall.enqueue(object : Callback<List<HistoryItem>> {
            override fun onResponse(
                call: Call<List<HistoryItem>>, response: Response<List<HistoryItem>>
            ) {
                _list.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<HistoryItem>>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
