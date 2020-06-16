package com.funworld.heogiutien.ui.resource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.funworld.heogiutien.data.repository.ResourceRepository
import com.funworld.heogiutien.model.entity.Resource

class ResourceViewModel(private val resourceRepository: ResourceRepository) : ViewModel() {

    private val _resource = resourceRepository.resources

    val resource: LiveData<List<Resource>> = _resource
}