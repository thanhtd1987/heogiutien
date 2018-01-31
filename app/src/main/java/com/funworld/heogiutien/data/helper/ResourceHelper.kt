package com.funworld.heogiutien.data.helper

import android.util.Log
import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource
import org.joda.time.DateTime

class ResourceHelper {

    companion object {
        fun addResource(name: String, description: String){
            var resource = Resource.getResourceByName(name)
            if(resource == null){
                var resource = Resource(name, description)
                val date = DateTime()
                resource.openingDate = date.toDateTime().millis
                Log.d("DEBUG", "check open date "+DateTime(resource.openingDate).toString())
                resource.closingDate = date.dayOfMonth().withMaximumValue().millis
                Log.d("DEBUG", "check close date "+DateTime(resource.closingDate).toString())
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