package com.example.database.utils

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Field
import java.util.Date

abstract class Audit {
    @CreatedDate
    @Field("created_at")
    var createdAt: Date = Date()
    @LastModifiedDate
    @Field("updated_at")
    var updatedAt: Date = Date()
    @Version
    @Field("_v")
    var version: Long = 0
}