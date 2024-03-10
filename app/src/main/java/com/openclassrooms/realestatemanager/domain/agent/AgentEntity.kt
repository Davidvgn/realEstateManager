package com.openclassrooms.realestatemanager.domain.agent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Agent")
data class AgentEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
)
