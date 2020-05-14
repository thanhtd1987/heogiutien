package com.funworld.heogiutien.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.funworld.heogiutien.model.Expense

@Dao
interface ExpenseDao {

    @Query("SELECT * from expense_table ORDER BY updated_at")
    fun getLatestExpenses(): LiveData<List<Expense>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Query("DELETE from expense_table")
    suspend fun deleteAll()

//    fun get50LatestExpenses() : List<Expense>{
//        fun getExpensesInPeriod(resourceId: Int, from: Long, to: Long) : List<Expense>{
}