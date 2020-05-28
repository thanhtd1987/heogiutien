package com.funworld.heogiutien.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.funworld.heogiutien.model.converter.DateTimeConverter
import java.time.LocalDateTime


/**
 * Created by ThanhTD on 11/2/2017.
 */

@Entity(tableName = "resource_table")
@TypeConverters(DateTimeConverter::class)
class Resource(
    //ten cua nguon tien
    @ColumnInfo(name = "name") var name: String,
    // mo ta ve nguon tien
    @ColumnInfo(name = "description") var description: String,
    // so du dau ki ( thong thuong la thang)
    @ColumnInfo(name = "opening_balance") var openingBalance: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "created_at")
    var createAt: LocalDateTime = LocalDateTime.now()

    // so du cuoi ki ( thong thuong la thang)
    @ColumnInfo(name = "closing_balance")
    var closingBalance: Int = 0

    // so du hien tai
    @ColumnInfo(name = "current_balance")
    var currentBalance: Int = 0

    // chi tieu cuoi cung
    @ColumnInfo(name = "last_expense")
    var lastExpenseId: Int? = 0

    init {
        closingBalance = openingBalance
        currentBalance = openingBalance
    }

}