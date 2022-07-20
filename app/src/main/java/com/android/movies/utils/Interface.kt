package com.android.movies.utils

fun interface OnClick<T> {

    operator fun invoke(item: T, position: Int)

}