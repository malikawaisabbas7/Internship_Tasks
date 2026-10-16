package com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.presentation.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.model.CallLogModel
import com.example.internshiptasks.Task17_Repository_Pattern_and_MVVM_on_PhoneCallsData.data.repository.CallLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentCallsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = CallLogRepository(application)

    private val _recentCalls =
        MutableLiveData<List<CallLogModel>>()
    val recentCalls: LiveData<List<CallLogModel>> = _recentCalls

    private val _errorMessage =
        MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading =
        MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadRecentCalls() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val calls = withContext(Dispatchers.IO) {
                    repository.getRecentCalls()
                }

                _recentCalls.value = calls
            } catch (exception: SecurityException) {
                _errorMessage.value =
                    "Call log permission is required to view recent calls."
            } catch (exception: Exception) {
                _errorMessage.value =
                    "Unable to load recent calls. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}
