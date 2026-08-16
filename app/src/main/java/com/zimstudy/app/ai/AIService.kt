package com.zimstudy.app.ai

interface AIService {

    suspend fun askAI(
        question: String
    ): String

}
