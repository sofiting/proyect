package com.example.taxast_taxi

import android.os.Parcel
import android.os.Parcelable

class RentActivity (

    val name: String,
    val dni: String,
    val picklocationrent: String,
    val selectDate: String,
    val pickTimeRent: String,
    val returnDate: String,
    val age: String

): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(dni)
        parcel.writeString(picklocationrent)
        parcel.writeString(selectDate)
        parcel.writeString(pickTimeRent)
        parcel.writeString(returnDate)
        parcel.writeString(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RentActivity> {
        override fun createFromParcel(parcel: Parcel): RentActivity {
            return RentActivity(parcel)
        }

        override fun newArray(size: Int): Array<RentActivity?> {
            return arrayOfNulls(size)
        }
    }
}