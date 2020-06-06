package com.funworld.heogiutien.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.funworld.heogiutien.data.local.dao.ExpenseDao
import com.funworld.heogiutien.data.local.dao.ResourceDao
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.model.entity.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Expense::class, Resource::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun resourceDao(): ResourceDao

    private class LocalDatabaseCallback(private val scope: CoroutineScope?) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { localDatabase ->
                scope?.launch {
                    val resourceDao = localDatabase.resourceDao()
                    val resource = Resource(
                        "Expense wallet",
                        "Main resource for making expenses",
                        0
                    )
                    resourceDao.insert(resource)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var instance: LocalDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope? = null): LocalDatabase {
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