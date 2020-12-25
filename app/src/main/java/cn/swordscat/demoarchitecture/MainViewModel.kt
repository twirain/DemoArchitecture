package cn.swordscat.demoarchitecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val greeting = MutableLiveData("Hello World!")
}