package com.seiya.trialcompose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountViewModel: ViewModel() {

    private val _count = MutableLiveData(0)
    val count:LiveData<Int> = _count

    //2ずつカウント
    fun countUp() {
        val number = _count.value ?: 0
        _count.value = number + 2
    }

    //数値をクリア
    fun clearUp() {
        _count.value = 0
    }
}