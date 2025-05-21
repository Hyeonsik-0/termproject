package com.example.termproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.termproject.model.Routine

class HomeViewModel : ViewModel() {

    // 루틴 목록을 관리하는 LiveData
    private val _routines = MutableLiveData<List<Routine>>(emptyList())
    val routines: LiveData<List<Routine>> = _routines

    // 루틴 추가 메서드
    fun addRoutine(title: String) {
        val currentList = _routines.value ?: emptyList()
        val newList = currentList + Routine(title)
        _routines.value = newList
    }

    // 루틴 수정 메서드
    fun updateRoutine(position: Int, newTitle: String) {
        val current = _routines.value.orEmpty().toMutableList()
        if (position in current.indices) {
            current[position] = current[position].copy(title = newTitle)
            _routines.value = current
        }
    }

    // 루틴 삭제 메서드
    fun deleteRoutine(position: Int) {
        val current = _routines.value.orEmpty().toMutableList()
        if (position in current.indices) {
            current.removeAt(position)
            _routines.value = current
        }
    }
}
