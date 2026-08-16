package com.zimstudy.app.ai


class OfflineAIService : AIService {


    override suspend fun askAI(
        question: String
    ): String {


        return """
        
        🤖 ZIMStudy AI Teacher
        
        Question:
        $question
        
        I am currently in offline mode.
        
        Available learning modes are being prepared:
        
        📚 Teach Mode
        I will explain concepts step by step.
        
        📝 Exam Mode
        I will create exam-style questions.
        
        🔍 Mistake Mode
        I will help identify errors.
        
        🎯 Revision Mode
        I will help create a study plan.
        
        💡 Example Mode
        I will provide practical examples.
        
        Real AI connection will allow full tutoring soon.
        
        """.trimIndent()

    }

}
