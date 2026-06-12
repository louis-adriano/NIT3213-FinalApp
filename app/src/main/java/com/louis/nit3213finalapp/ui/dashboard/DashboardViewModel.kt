package com.louis.nit3213finalapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louis.nit3213finalapp.data.model.Book
import com.louis.nit3213finalapp.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _dashboardState = MutableLiveData<DashboardState>()
    val dashboardState: LiveData<DashboardState> = _dashboardState

    fun loadBooks() {
        _dashboardState.value = DashboardState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getDashboard()
                if (response.isSuccessful && response.body() != null) {
                    _dashboardState.value = DashboardState.Success(response.body()!!.entities)
                } else {
                    _dashboardState.value = DashboardState.Error("Failed to load books")
                }
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error("Network error: ${e.message}")
            }
        }
    }
}

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val books: List<Book>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}
