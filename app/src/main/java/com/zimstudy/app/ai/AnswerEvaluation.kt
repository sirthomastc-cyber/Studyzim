package com.zimstudy.app.ai


data class AnswerEvaluation(

    val score: Int,

    val totalMarks: Int,

    val feedback: String,

    val improvement: String

)
