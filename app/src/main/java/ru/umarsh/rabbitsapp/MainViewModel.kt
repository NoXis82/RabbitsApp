package ru.umarsh.rabbitsapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.umarsh.rabbitsapp.data.Rabbit
import ru.umarsh.rabbitsapp.data.RabbitsApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val rabbitsApi: RabbitsApi
) : ViewModel() {

    private val _state = mutableStateOf(RabbitState())
    val state: State<RabbitState> = _state

    init {
        getRandomRabbit()
    }

     fun getRandomRabbit() {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(rabbit = rabbitsApi.getRandomRabbit(),isLoading = false)
            } catch (e: Exception) {
                Log.e(this.javaClass.name, "getRandomRabbit: ", e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }

    data class RabbitState(
        val rabbit: Rabbit? = null,
        val isLoading: Boolean = false
    )
}