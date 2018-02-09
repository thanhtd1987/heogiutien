package com.funworld.heogiutien.data.dao

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select


/**
 * Created by ThanhTD on 11/2/2017.
 */

@Table(name = "Resource")
class Resource() : Model() {

    //    @Column(name = "resource_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
//    var resourceId: Int = 0
    //ten cua nguon tien
    @Column(name = "name")
    lateinit var name: String
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
    // chi tieu cuoi cung
    @Column(name = "last_expense")
    var lastExpense: Expense? = null

    constructor(name: String, description: String) : this() {
        this.name = name
        this.description = description
    }

    fun expenses(): MutableList<Expense> {
        return getMany(Expense::class.java, "resource")
    }


    companion object {
        fun getResourceByName(name: String): Resource {
            return Select().from(Resource::class.java)
                    .where("name=?", name)
                    .executeSingle()
        }

        fun getAll() = Select().from(Resource::class.java).execute<Resource>()

    }
}