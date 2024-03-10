package com.openclassrooms.realestatemanager.data.agent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.realestatemanager.domain.agent.model.AgentEntity

@Dao
interface AgentDao {
    @Insert
    fun insert(agentEntity: AgentEntity)

    @Query("SELECT * FROM agent")
    suspend fun getAllAgent(): List<AgentEntity>

    @Query("SELECT * FROM agent WHERE id = :agentId")
    suspend fun getAgentById(agentId: Long): AgentEntity?
}
