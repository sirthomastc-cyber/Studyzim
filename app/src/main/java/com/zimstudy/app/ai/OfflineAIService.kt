package com.zimstudy.app.ai


class OfflineAIService {


    fun askAI(
        message: String
    ): String {


        return when {

            message.contains(
                "explain",
                ignoreCase = true
            ) -> {

                "I will explain this topic step by step with examples."

            }


            message.contains(
                "quiz",
                ignoreCase = true
            ) -> {

                "Here is a practice question. Try answering it first."

            }


            else -> {

                "I am your ZIMStudy AI Teacher. Ask me about your subject."

            }

        }

    }

}
