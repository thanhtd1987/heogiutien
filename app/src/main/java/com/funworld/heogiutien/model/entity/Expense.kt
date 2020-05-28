package com.funworld.heogiutien.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.funworld.heogiutien.utils.extention.getAsPattern
import com.funworld.heogiutien.model.converter.DateTimeConverter
import java.time.LocalDateTime


/**
 * Created by ThanhTD on 6/25/2017.
 */

@Entity(tableName = "expense_table")
@TypeConverters(DateTimeConverter::class)
class Expense(
    //loai chi tieu: chi (-), them vao(+)
    @ColumnInfo(name = "type") var type: String,
    //luong tien dung cho chi tieu
    @ColumnInfo(name = "money_amount") var amount: Int,
    //muc dich cua chi tieu
    @ColumnInfo(name = "purpose") var purpose: String,
    // nguon tien dung cho chi tieu
    @ColumnInfo(name = "resource") var resourceId: Int,
    //thoi gian tao chi tieu
    @ColumnInfo(name = "created_at") val createAt: LocalDateTime
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    //thoi gian cua lan chinh sua cuoi cung
    @ColumnInfo(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    //TODO dung cho chuc nang Ghi no / muon tien

    // loai chi tieu lien quan den nguoi khac : muon (+), cho muon (-)
    @ColumnInfo(name = "related_type")
    var relatedType: String? = null

    // nguoi lien quan den chi tieu muon/cho muon
    @ColumnInfo(name = "related_person")
    var relatedPerson: String? = null

    @ColumnInfo(name = "relate_amount")
    var relatedAmount: String? = null // cac chi tieu lien quan, tach chi tieu...

    init {
//        updatedAt = createAt
    }

    enum class Type(type: String) {
        EXPENSE("-"),
        DEPOSITE("+")
    }

    fun getShortCreatedTime() = createAt.getAsPattern(IN_MONTH_FORMAT)

    companion object {
        private const val IN_DAY_FORMAT = "HH:mm"
        private const val IN_MONTH_FORMAT = "dd-MM HH:mm"
        private const val FULL_FORMAT = "YYYY-dd-MM HH:mm"
    }
}
