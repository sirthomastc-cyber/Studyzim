package com.zimstudy.app.data

data class PerformanceRecord(

    val subjectName: String,

    val quizAccuracy: Double,

    val pastPaperAccuracy: Double,

    val consistency: Double,

    val lastUpdated: Long = System.currentTimeMillis()

)
