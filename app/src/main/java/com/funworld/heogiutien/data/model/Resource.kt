package com.funworld.heogiutien.data.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select


/**
 * Created by ThanhTD on 11/2/2017.
 */

@SuppressLint("ParcelCreator")
@Table(name = "Resource")
class Resource() : Model(), Parcelable {

    //    @Column(name = "resource_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
//    var resourceId: Int = 0
    //ten cua nguon tien
    @Column(name = "name")
    lateinit var name: String
    @Column(name = "short_name")
    var shortName = ""
    // mo ta ve nguon tien
    @Column(name = "description")
    lateinit var description: String
    // so du dau ki ( thong thuong la thang)
    @Column(name = "opening_balance")
    var openingBalance: Int = 0
    @Column(name = "opening_date")
    var openingDate: Long = 0

    // so du cuoi ki ( thong thuong la thang)
    @Column(name = "closing_balance")
    var closingBalance: Int = 0
    @Column(name = "closing_date")
    var closingDate: Long = 0

    // so du hien tai
    @Column(name = "current_balance")
    var currentBalance: Int = 0

    @Column(name = "currency")
    var currency: String = "Vietnam Dong"
    @Column(name = "currency_unit")
    var currencyUnit: String = "k"
    // chi tieu cuoi cung
    @Column(name = "last_expense")
    var lastExpense: Expense? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        shortName = parcel.readString()
        description = parcel.readString()
        openingBalance = parcel.readInt()
        openingDate = parcel.readLong()
        closingBalance = parcel.readInt()
        closingDate = parcel.readLong()
        currentBalance = parcel.readInt()
        currency = parcel.readString()
        currencyUnit = parcel.readString()
    }

    constructor(name: String, description: String) : this() {
        this.name = name
        this.description = description
    }

    fun expenses(): MutableList<Expense> {
        return getMany(Expense::class.java, "resource")
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(shortName)
        parcel.writeString(description)
        parcel.writeInt(openingBalance)
        parcel.writeLong(openingDate)
        parcel.writeInt(closingBalance)
        parcel.writeLong(closingDate)
        parcel.writeInt(currentBalance)
        parcel.writeString(currency)
        parcel.writeString(currencyUnit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Resource> {
        override fun createFromParcel(parcel: Parcel): Resource {
            return Resource(parcel)
        }

        override fun newArray(size: Int): Array<Resource?> {
            return arrayOfNulls(size)
        }

        fun getResourceByName(name: String): Resource? {
            val list: MutableList<Resource> = Select().from(Resource::class.java)
                    .where("name=?", name)
                    .execute()
            if (list.isEmpty())
                return null
            else
                return list[0]
        }

        fun getAll() = Select().from(Resource::class.java).execute<Resource>()

    }
}