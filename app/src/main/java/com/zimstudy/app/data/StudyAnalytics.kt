package com.zimstudy.app.data


class StudyAnalytics {


    fun calculateStudyFrequency(
        sessions: List<StudySession>,
        subject: String
    ): Double {


        val subjectSessions =
            sessions.filter {
                it.subjectName == subject
            }


        if(subjectSessions.isEmpty()) {
            return 0.0
        }


        return subjectSessions.size.toDouble()

    }


}
