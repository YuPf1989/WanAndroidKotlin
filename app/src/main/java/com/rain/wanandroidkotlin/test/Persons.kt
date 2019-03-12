package com.rain.wanandroidkotlin.test

import android.os.Parcel
import android.os.Parcelable

/**
 * Author:rain
 * Date:2019/1/10 15:36
 * Description:
 */

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
data class Persons(
        val name: String,
        val age: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Persons> {
        override fun createFromParcel(parcel: Parcel): Persons {
            return Persons(parcel)
        }

        override fun newArray(size: Int): Array<Persons?> {
            return arrayOfNulls(size)
        }
    }
}