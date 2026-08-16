package com.zimstudy.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// =========================
// STUDENT PROFILE
// =========================

@Entity(tableName = "student_profile")
data class StudentProfile(

    @PrimaryKey
    val id: Int = 1,

    val name: String,

    val school: String,

    val grade: String,

    val examBoard: String,

    val examYear: String

)



// =========================
// SUBJECT
// =========================

@Entity(tableName = "subjects")
data class SubjectEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val targetGrade: String = "A"

)



// =========================
// EXAMS
// =========================

@Entity(tableName = "exams")
data class ExamEntry(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val subjectName: String,

    val paperNumber: String,

    val examDateMillis: Long

)



// =========================
// STUDY SESSION
// =========================

@Entity(tableName = "study_sessions")
data class StudySession(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val subjectName: String,

    val topic: String,

    val startedAtMillis: Long,

    val durationMinutes: Int,

    val completed: Boolean

)
