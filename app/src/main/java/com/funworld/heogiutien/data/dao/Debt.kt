import android.support.annotation.StringDef
import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.funworld.heogiutien.data.dao.Expense

@Table(name = "Debt")
class Debt: Model(){

    @Column(name = "debt_type")
    @DebtType lateinit var type: String
    @Column(name = "amount")
    var amount: Int = 0
    @Column(name = "purpose")
    lateinit var purpose: String
    @Column(name = "expensive_id")
    lateinit var expenseId: Expense
    //thoi gian tao chi tieu
    @Column(name = "created_at")
    var createdAt: Long = 0
    //thoi gian cua lan chinh sua cuoi cung
    @Column(name = "updated_at")
    var updatedAt: Long = 0
    @Column(name = "related_person")
    var relatedPerson: Long = 0


    companion object {
        const val BORROW_TYPE = "borrow"
        const val DEBT_TYPE = "debt"

        @StringDef(BORROW_TYPE, DEBT_TYPE)
        @Retention(AnnotationRetention.SOURCE)
        annotation class DebtType
    }
}