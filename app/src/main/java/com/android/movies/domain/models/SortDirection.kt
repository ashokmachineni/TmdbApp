package com.android.movies.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SortDirection: Parcelable {
    ASC,
    DESC,
}