package com.funworld.heogiutien.data.dao

import android.os.Parcel
import android.os.Parcelable
import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select



/**
 * Created by ThanhTD on 6/25/2017.
 */

@Table(name = "Expense")
class Expense() : Model() {

//    @Column(name = "expense_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
//    var expenseId: Int = 0
    //loai chi tieu: chi (-), them vao(+)
    @Column(name = "type")
    lateinit var type: String
    //luong tien dung cho chi tieu
    @Column(name = "money_amount")
    var amount: Int = 0
    // nguon tien dung cho chi tieu
    @Column(name = "resource", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    lateinit var resourceId: Resource
    //muc dich cua chi tieu
    @Column(name = "purpose")
    lateinit var purpose: String
    //thoi gian tao chi tieu
    @Column(name = "created_at")
    var createAt: Long = 0
    //thoi gian cua lan chinh sua cuoi cung
    @Column(name = "updated_at")
    var updatedAt: Long = 0
    // loai chi tieu lien quan den nguoi khac : muon (+), cho muon (-)
    @Column(name = "related_type")
    var relatedType: String? = null
    // nguoi lien quan den chi tieu muon/cho muon
    @Column(name = "related_person")
    var relatedPerson: String? = null

    var relatedExpense: String? = null // cac chi tieu lien quan, tach chi tieu...


    companion object {
        fun get50LatestExpenses(resource: Resource) : MutableList<Expense>{
            return Select()
                    .from(Expense::class.java)
                    .where("resource = ?", resource.id)
                    .orderBy("created_at ASC")
                    .limit(50)

                    .execute()
        }

        fun getExpensesInPeriod(resource: Resource, from: Long, to: Long) : MutableList<Expense>{
            return Select()
                    .from(Expense::class.java)
                    .where("resource = ?", resource.id)
                    .orderBy("created_at ASC")
                    .limit(50)
                    .execute()
        }

    }

}
