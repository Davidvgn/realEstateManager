package com.openclassrooms.realestatemanager.data.agent

import com.openclassrooms.realestatemanager.domain.agent.AgentRepository
import com.openclassrooms.realestatemanager.domain.agent.model.AgentEntity
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
