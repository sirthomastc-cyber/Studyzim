package com.zimstudy.app.ai


class AIMarkingService {


    fun markAnswer(
        answer: String,
        question: ExamQuestion
    ): AnswerEvaluation {



        val score =
            if(answer.length > 50)
                4
            else
                2



        return AnswerEvaluation(

            score = score,

            totalMarks = question.marks,

            feedback =
            """
            Your answer has been reviewed.
            
            You included some relevant ideas.
            """.trimIndent(),


            improvement =
            """
            Add more scientific keywords
            and link your explanation to the question.
            """.trimIndent()

        )

    }

}
