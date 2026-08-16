package com.zimstudy.app.ai


class AIExaminerService {


    fun generateQuestion(
        subject: String,
        topic: String
    ): ExamQuestion {


        return ExamQuestion(

            questionNumber = 1,

            questionText =
            "Explain the main concepts of $topic.",

            subject = subject,

            topic = topic,

            marks = 5,

            difficulty = "Medium"

        )

    }


}
