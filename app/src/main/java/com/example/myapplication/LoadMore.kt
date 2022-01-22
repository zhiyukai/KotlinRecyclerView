package com.example.myapplication

data class LoadMore(var showText: String, var isShow: Boolean) {
    constructor() : this(showText = "我是底部", isShow = true)
}