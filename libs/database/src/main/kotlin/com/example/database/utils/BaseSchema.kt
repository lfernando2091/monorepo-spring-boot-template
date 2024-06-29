package com.example.database.utils

import com.example.database.models.Status
import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient

abstract class BaseSchema: Audit() {
    @Id
    @get:JsonIgnore
    var _id: ObjectId = ObjectId()
    @get:Transient
    val id: String get() = _id.toString()
    var status: Status = Status.ACTIVE
}