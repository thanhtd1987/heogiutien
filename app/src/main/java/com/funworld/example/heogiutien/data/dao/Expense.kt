package com.funworld.example.heogiutien.data.dao

import android.os.Parcel
import android.os.Parcelable

import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by ThanhTD on 6/25/2017.
 */

@Table(name = "Expense")
class Expense() : Parcelable {
    @Column(name = "expense_id", unique = true)
    var expenseId: Int = 0
    @Column(name = "type")
    var type: String? = null
    @Column(name = "money_amount")
    var amount: Int = 0
    @Column(name = "purpose")
    var purpose: String? = null
    @Column(name = "created_at")
    var createAt: Long = 0
    @Column(name = "updated_at")
    var updatedAt: Long = 0
    @Column(name = "related_type")
    var relatedType: String? = null
    @Column(name = "related_person")
    var relatedPerson: String? = null
    @Column(name = "resource_id")
    var resourceId: Int = 0
    var relatedExpense: String? = null // cac chi tieu lien quan, tach chi tieu...

    constructor(parcel: Parcel) : this() {
        expenseId = parcel.readInt()
        type = parcel.readString()
        amount = parcel.readInt()
        purpose = parcel.readString()
        createAt = parcel.readLong()
        updatedAt = parcel.readLong()
        relatedType = parcel.readString()
        relatedPerson = parcel.readString()
        resourceId = parcel.readInt()
        relatedExpense = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(expenseId)
        parcel.writeString(type)
        parcel.writeInt(amount)
        parcel.writeString(purpose)
        parcel.writeLong(createAt)
        parcel.writeLong(updatedAt)
        parcel.writeString(relatedType)
        parcel.writeString(relatedPerson)
        parcel.writeInt(resourceId)
        parcel.writeString(relatedExpense)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }


}
