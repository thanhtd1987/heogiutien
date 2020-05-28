package com.funworld.heogiutien.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.funworld.heogiutien.model.entity.Resource

@Dao
interface ResourceDao {

    @Query("SELECT * from resource_table")
    fun getAllResource(): LiveData<List<Resource>>

    @Query("SELECT * from resource_table WHERE id == :resourceId")
    fun getResourceById(resourceId: Int): LiveData<Resource>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(resource: Resource)

    @Update
    suspend fun update(resource: Resource)
}