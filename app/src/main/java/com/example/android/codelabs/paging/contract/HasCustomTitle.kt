package com.example.android.codelabs.paging.contract


import androidx.annotation.StringRes

interface HasCustomTitle {

    @StringRes
    fun getTitleRes(): Int

}

interface HasCustomTitleString {

    fun getTitleString(): String

}