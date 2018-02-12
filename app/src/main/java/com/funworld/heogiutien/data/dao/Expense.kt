package com.funworld.heogiutien.data.dao

import android.support.annotation.StringDef
import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select
import org.joda.time.DateTime


/**
 * Created by ThanhTD on 6/25/2017.
 */

@Table(name = "Expense")
class Expense() : Model() {

//    @Column(name = "expense_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
//    var expenseId: Int = 0
    //loai chi tieu: chi (-), them vao(+)
    @Column(name = "type")
    @ExpenseType lateinit var type: String
    //luong tien dung cho chi tieu
    @Column(name = "money_amount")
    var amount: Int = 0
    //muc dich cua chi tieu
    @Column(name = "purpose")
    lateinit var purpose: String
    // nguon tien dung cho chi tieu
    @Column(name = "resource", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    lateinit var resourceId: Resource
    @Column(name = "note")
    var note: String? = null
    //thoi gian tao chi tieu
    @Column(name = "created_at")
    var createdAt: Long = 0
    //thoi gian cua lan chinh sua cuoi cung
    @Column(name = "updated_at")
    var updatedAt: Long = 0

    var relatedExpense: String = "" // cac chi tieu lien quan, tach chi tieu...

    fun getCreatedTime(): String {
        return DateTime(createdAt).hourOfDay().toString()
    }

    fun getCreatedDateTime(): String {
        return DateTime(createdAt).toDateTime().toString()
    }

    companion object {
        const val DEPOSIT_TYPE = "deposit"
        const val WITHDRAW_TYPE = "withdraw"

        @StringDef(DEPOSIT_TYPE, WITHDRAW_TYPE)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ExpenseType

        fun get50LatestExpenses(resource: Resource): MutableList<Expense> {
            return Select()
                    .from(Expense::class.java)
                    .where("resource = ?", resource.id)
                    .orderBy("created_at ASC")
                    .limit(50)
                    .execute()
        }

        fun getExpensesInPeriod(resource: Resource, from: Long, to: Long): MutableList<Expense> {
            return Select()
                    .from(Expense::class.java)
                    .where("resource = ? AND created_at > ? AND created_at < ?", resource.id, from, to)
                    .orderBy("created_at ASC")
                    .execute()
        }

        fun getExpenseByDate(resource: Resource, date: Long): MutableList<Expense> {
            return getExpensesInPeriod(resource, date, date + (24 * 60 * 60 * 1000))
        }

    }

}
