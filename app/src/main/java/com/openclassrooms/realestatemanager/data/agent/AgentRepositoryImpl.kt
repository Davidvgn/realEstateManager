package com.openclassrooms.realestatemanager.data.agent

import com.openclassrooms.realestatemanager.domain.agent.AgentEntity
import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import javax.inject.Inject

class AgentRepositoryImpl
    @Inject
    constructor(
        private val agentDao: AgentDao,
    ) : AgentRepository {
        override suspend fun getAgentById(agentId: Long): AgentEntity? {
            return agentDao.getAgentById(agentId)
        }

        override suspend fun getAllAgent(): List<AgentEntity> {
            return agentDao.getAllAgent()
        }
    }
