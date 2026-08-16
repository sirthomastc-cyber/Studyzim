package com.zimstudy.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



// =========================
// STUDENT PROFILE DAO
// =========================

@Dao
interface StudentProfileDao {


    @Query("SELECT * FROM student_profile LIMIT 1")
    fun getProfile(): kotlinx.coroutines.flow.Flow<StudentProfile?>


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun saveProfile(
        profile: StudentProfile
    )

}



// =========================
// SUBJECT DAO
// =========================

@Dao
interface SubjectDao {


    @Query("SELECT * FROM subjects")
    fun getAllSubjects():
            kotlinx.coroutines.flow.Flow<List<SubjectEntity>>



    @Insert
    suspend fun addSubject(
        subject: SubjectEntity
    )



    @Delete
    suspend fun deleteSubject(
        subject: SubjectEntity
    )

}



// =========================
// EXAM DAO
// =========================

@Dao
interface ExamEntryDao {


    @Query("SELECT * FROM exams")
    fun getAllExams():
            kotlinx.coroutines.flow.Flow<List<ExamEntry>>



    @Insert
    suspend fun addExam(
        exam: ExamEntry
    )

}



// =========================
// STUDY SESSION DAO
// =========================

@Dao
interface StudySessionDao {


    @Insert
    suspend fun addSession(
        session: StudySession
    )



    @Query("SELECT * FROM study_sessions")
    fun getAllSessions():
            List<StudySession>


}
