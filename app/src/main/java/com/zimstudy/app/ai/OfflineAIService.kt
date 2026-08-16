package com.zimstudy.app.ai


class OfflineAIService : AIService {


    override suspend fun askAI(
        question: String
    ): String {


        return when {


            question.contains(
                "explain",
                ignoreCase = true
            ) -> {

                "I will explain this topic step by step using simple examples."

            }


            question.contains(
                "quiz",
                ignoreCase = true
            ) -> {

                "Here is a practice question based on your topic."

            }


            question.contains(
                "mark",
                ignoreCase = true
            ) -> {

                "I will analyse your answer and provide feedback."

            }


            else -> {

                "Offline AI is analysing: $question"

            }


        }

    }

}
