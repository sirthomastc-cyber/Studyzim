package com.zimstudy.app.ai


class OfflineAIService : AIService {


    override suspend fun askAI(

        question: String,

        context: StudyContext,

        mode: AIMode

    ): String {


        return """

        🤖 ZIMStudy AI Teacher


        Subject:
        ${context.subject}


        Topic:
        ${context.topic}


        Level:
        ${context.level}


        Mode:
        $mode


        Question:
        $question



        I am currently offline.


        When connected to the AI engine I will:

        • Explain ZIMSEC concepts
        • Generate exam questions
        • Mark answers
        • Identify weaknesses
        • Create revision plans


        """.trimIndent()

    }


}
