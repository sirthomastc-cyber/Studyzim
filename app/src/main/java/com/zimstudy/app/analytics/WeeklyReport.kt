package com.zimstudy.app.analytics

data class WeeklyReport(
    val studentName: String,
    val totalStudyHours: Double,
    val plannedHours: Double,
    val averageAccuracy: Double,
    val masteryChange: Double,
    val strongestSubject: String,
    val weakestSubject: String,
    val recommendation: String
) {

    fun completionPercentage(): Double {

        if (plannedHours == 0.0) {
            return 0.0
        }

        return (totalStudyHours / plannedHours * 100)
            .coerceAtMost(100.0)
    }


    fun summary(): String {

        return """
        Weekly Study Report
        
        Student:
        $studentName
        
        Study completed:
        ${completionPercentage()}%
        
        Average accuracy:
        $averageAccuracy%
        
        Mastery change:
        +$masteryChange%
        
        Strongest subject:
        $strongestSubject
        
        Priority improvement:
        $weakestSubject
        
        Recommendation:
        $recommendation
        """.trimIndent()

    }
}
