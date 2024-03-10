package com.openclassrooms.realestatemanager.domain.agent

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
