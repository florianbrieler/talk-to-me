package com.example.talktome

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "talks")
data class Talk(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val message: String,
    val enabled: Boolean = true,
    val triggerType: TriggerType = TriggerType.TIME_INTERVAL,
    val intervalMinutes: Int? = null,
    val specificTimeMillis: Long? = null,
    val locationName: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val radius: Float? = null,
    val onEnter: Boolean? = null
)

enum class TriggerType {
    TIME_INTERVAL,
    TIME_SPECIFIC,
    LOCATION
}
