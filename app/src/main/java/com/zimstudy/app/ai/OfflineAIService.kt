package com.zimstudy.app.ai


class OfflineAIService : AIService {


    override suspend fun askAI(
        question: String
    ): String {


        return """
        ZIMStudy AI Teacher

        Question:
        $question

        I am currently in offline mode.

        Soon I will be able to:
        • Explain concepts
        • Create exam questions
        • Mark answers
        • Analyse textbooks
        """.trimIndent()

    }

}
