package com.funworld.heogiutien.data.repository

import com.funworld.heogiutien.data.local.dao.ResourceDao
import com.funworld.heogiutien.model.entity.Resource

class ResourceRepository(private val resourceDao: ResourceDao) {

    val resources = resourceDao.getAllResource()

    suspend fun insertResource(resource: Resource) {
        resourceDao.insert(resource)
    }
}