package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val TAG = "LoginViewModel"
    val name: MutableLiveData<String> = MutableLiveData()
    val desc: MutableLiveData<String> = MutableLiveData()

    fun test() {
        Log.d(TAG, "test")
        name.value = "张三"
    }

    fun test3() {
        Log.d(TAG, "test3")
        desc.value = "我是一个兵"
    }
}