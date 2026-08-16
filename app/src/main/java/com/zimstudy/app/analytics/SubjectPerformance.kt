package com.zimstudy.app.analytics

data class SubjectPerformance(

    val subjectName: String,

    // Estimated mastery percentage
    val masteryPercentage: Double,

    // Predicted grade
    val predictedGrade: String,

    // Confidence level of prediction
    val confidence: String,

    // Current priority level
    val priority: String

)
