package com.openclassrooms.realestatemanager.domain.agent

import com.openclassrooms.realestatemanager.domain.agent.model.AgentEntity

interface AgentRepository {
    suspend fun getAgentById(agentId: Long): AgentEntity?

    suspend fun getAllAgent(): List<AgentEntity>
}
