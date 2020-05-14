package com.funworld.heogiutien.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.funworld.heogiutien.model.Resource

@Dao
interface ResourceDao {

    @Query("SELECT * from resource_table")
    fun getAllResource(): LiveData<List<Resource>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(resource: Resource)
}