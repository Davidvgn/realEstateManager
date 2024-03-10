package com.openclassrooms.realestatemanager.domain.agent

import com.openclassrooms.realestatemanager.domain.agent.model.AgentEntity
import javax.inject.Inject

class GetAgentsByIdUseCase
    @Inject
    constructor(
        private val agentRepository: AgentRepository,
    ) {
        suspend operator fun invoke(agentId: Long): AgentEntity? {
            return agentRepository.getAgentById(agentId)
        }
    }
