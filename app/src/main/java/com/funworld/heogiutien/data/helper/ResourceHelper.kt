package com.funworld.heogiutien.data.helper

import com.funworld.heogiutien.data.dao.Resource
import org.joda.time.DateTime

class ResourceHelper {

    companion object {
        fun addResource(name: String, description: String){
            var resource = Resource.getResourceByName(name)
            if(resource == null){
                resource = Resource(name, description)
                val date = DateTime()
                resource.openingDate = date.toDateTime().millis
                resource.closingDate = date.dayOfMonth().withMaximumValue().millis
                resource.save()
            }
        }

        fun deleteByName(name: String){
            var resource = Resource.getResourceByName(name)
            if(resource != null){
                resource.delete()
            }
        }
    }
}