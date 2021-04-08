package com.example.alarmmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    val title: String,
    val hour: Int,
    val minute: Int,
    val started: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
)
