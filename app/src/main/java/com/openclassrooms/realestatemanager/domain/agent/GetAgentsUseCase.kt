package com.openclassrooms.realestatemanager.domain.agent

import com.openclassrooms.realestatemanager.domain.agent.model.AgentEntity
import javax.inject.Inject

class GetAgentsUseCase
    @Inject
    constructor(
        private val agentRepository: AgentRepository,
    ) {
        suspend operator fun invoke(): List<AgentEntity> {
            return agentRepository.getAllAgent()
        }
    }
