package com.zimstudy.app.ai


class OfflineAIService : AIService {


    override fun askAI(
        message: String
    ): String {


        return when {

            message.contains(
                "explain",
                ignoreCase = true
            ) -> {

                "I will explain this topic step by step."

            }


            message.contains(
                "quiz",
                ignoreCase = true
            ) -> {

                "I will generate a practice question."

            }


            message.contains(
                "mark",
                ignoreCase = true
            ) -> {

                "I will evaluate your answer."

            }


            else -> {

                "Offline AI is analysing your request."

            }

        }

    }

}
