package com.funworld.heogiutien.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.funworld.heogiutien.data.dao.ExpenseDao
import com.funworld.heogiutien.data.dao.ResourceDao
import com.funworld.heogiutien.model.Expense
import com.funworld.heogiutien.model.Resource
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Expense::class, Resource::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun resourceDao(): ResourceDao

    private class LocalDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            instance?.let {localDatabase ->
//                scope.launch {
//                    var resourceDao = localDatabase.resourceDao()
//                    var resource = Resource(
//                        "Expense wallet",
//                        "Main resource for making expenses",
//                        0)
//                    resourceDao.insert(resource)
//                }
//            }
//        }
    }

    companion object {
        @Volatile
        private var instance: LocalDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LocalDatabase {
            val tempInstance = instance
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance1 = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "heogiutien_db"
                )
                    .addCallback(LocalDatabaseCallback(scope))
                    .build()
                instance = instance1
                return instance1
            }
        }
    }
}