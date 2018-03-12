package com.funworld.heogiutien.data.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringDef
import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


/**
 * Created by ThanhTD on 6/25/2017.
 */

@Table(name = "Expense")
class Expense() : Model(), Parcelable {

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

    constructor(parcel: Parcel) : this() {
        type = parcel.readString()
        amount = parcel.readInt()
        purpose = parcel.readString()
        resourceId = parcel.readParcelable(Resource::class.java.classLoader)
        note = parcel.readString()
        createdAt = parcel.readLong()
        updatedAt = parcel.readLong()
        relatedExpense = parcel.readString()
    }

    fun getCreatedTime()= DateTimeFormat.forPattern("HH:mm").print(createdAt)

    fun getCreatedDateTime() = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm").print(createdAt)

    override fun toString() = "[type: $type, amount: $amount, purpose: $purpose, createdAt: $createdAt, resource id: $resourceId]"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeInt(amount)
        parcel.writeString(purpose)
        parcel.writeParcelable(resourceId, flags)
        parcel.writeString(note)
        parcel.writeLong(createdAt)
        parcel.writeLong(updatedAt)
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

        fun getInPeriod(resource: Resource, from: Long, to: Long): MutableList<Expense> {
            return Select()
                    .from(Expense::class.java)
                    .where("resource = ? AND created_at > ? AND created_at < ?", resource.id, from, to)
                    .orderBy("created_at ASC")
                    .execute()
        }

        fun getByDate(resource: Resource, date: Long): MutableList<Expense> {
            return getInPeriod(resource,
                    date,
                    DateTime(date).withTime(23, 59, 59, 999).millis)
        }

        fun getSumOfPeriod(resource: Resource, from: Long, to: Long): Int{
//            return getByDate(resource, date).map { it.amount }.sum()
            return getInPeriod(resource, from, to).sumBy { it.amount }
        }

        fun getAll() = Select().from(Expense::class.java).execute<Expense>()

        fun getByResource(resource: Resource) = Select().from(Expense::class.java)
                .where("resource=?", resource.id).execute<Expense>()
    }
}
