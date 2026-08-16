package com.zimstudy.app.ai


class ExamManager {


    private var currentSession:
            ExamSession? = null



    fun startExam(
        subject: String,
        topic: String
    ): ExamSession {


        val examiner =
            AIExaminerService()



        val question =
            examiner.generateQuestion(
                subject,
                topic
            )



        currentSession =
            ExamSession(
                question
            )



        return currentSession!!

    }





    fun submitAnswer(
        answer: String
    ): AnswerEvaluation? {


        val session =
            currentSession
                ?: return null



        session.studentAnswer =
            answer



        val marker =
            AIMarkingService()



        return marker.markAnswer(
            answer,
            session.question
        )


    }


}
