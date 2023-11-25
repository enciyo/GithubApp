package com.enciyo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sample(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
)