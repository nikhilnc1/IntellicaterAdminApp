package com.example.waveoffoodadmin.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


class OrderDetails() : Serializable {
    var userUid: String? = null
    var tokenNum: String? = null
    var foodNames: MutableList<String>? = null
    var foodPrices: MutableList<String>? = null
    var foodQuantities: MutableList<Int>? = null
    var totalPrice: String? = null
    var orderAccepted: Boolean = false
    var paymentReceived: Boolean = false
    var itemPushKey: String? = null
    var currentTime: Long = 0
    var menuKey: MutableList<String>? = null

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        tokenNum = parcel.readString()
        totalPrice = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
        menuKey = parcel.createStringArrayList()
    }

    fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}