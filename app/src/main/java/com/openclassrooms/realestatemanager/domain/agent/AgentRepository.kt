package com.openclassrooms.realestatemanager.domain.agent

interface AgentRepository {
    suspend fun getAgentById(agentId: Long): AgentEntity?

    suspend fun getAllAgent(): List<AgentEntity>
}
