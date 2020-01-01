package com.tobysoft.originhub.model.github


import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class UserSearchResult(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
) : Parcelable