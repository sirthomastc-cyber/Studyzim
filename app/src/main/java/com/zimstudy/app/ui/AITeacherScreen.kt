package com.zimstudy.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zimstudy.app.StudyViewModel
import com.zimstudy.app.ai.*
import kotlinx.coroutines.launch


@Composable
fun AITeacherScreen(
    viewModel: StudyViewModel
) {

    var question by remember {
        mutableStateOf("")
    }

    var selectedMode by remember {
        mutableStateOf(AIMode.TEACH)
    }

    var selectedSubject by remember {
        mutableStateOf("General")
    }

    var selectedTopic by remember {
        mutableStateOf("General Revision")
    }


    val subjects by viewModel.subjects.collectAsState()


    val topics =
        TopicRepository.getTopics(selectedSubject)


    val messages =
        remember {
            mutableStateListOf<AIMessage>()
        }


    val scope =
        rememberCoroutineScope()


    val ai =
        OfflineAIService()



    val context =
        StudyContext(
            subject = selectedSubject,
            level = "ZIMSEC O-Level",
            topic = selectedTopic
        )



    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        Text(
            "🤖 ZIMStudy AI Teacher",
            style = MaterialTheme.typography.headlineSmall
        )


        Spacer(
            Modifier.height(10.dp)
        )


        Text(
            "Subject: $selectedSubject"
        )


        LazyRow {

            items(subjects) { subject ->

                Button(

                    onClick = {

                        selectedSubject =
                            subject.name

                        selectedTopic =
                            "General Revision"

                    },

                    modifier =
                    Modifier.padding(4.dp)

                ) {

                    Text(subject.name)

                }

            }

        }



        Spacer(
            Modifier.height(10.dp)
        )



        Text(
            "Topic: $selectedTopic"
        )



        LazyRow {

            items(topics) { topic ->


                Button(

                    onClick = {

                        selectedTopic =
                            topic.name

                    },

                    modifier =
                    Modifier.padding(4.dp)

                ) {

                    Text(topic.name)

                }

            }

        }




        Spacer(
            Modifier.height(10.dp)
        )



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
            Arrangement.SpaceBetween
        ) {


            Button(
                onClick = {
                    selectedMode =
                        AIMode.TEACH
                }
            ) {
                Text("Teach")
            }



            Button(
                onClick = {
                    selectedMode =
                        AIMode.EXAM
                }
            ) {
                Text("Exam")
            }



            Button(
                onClick = {
                    selectedMode =
                        AIMode.EXPLAIN_MISTAKE
                }
            ) {
                Text("Mistake")
            }


        }





        LazyColumn(

            modifier =
            Modifier
                .weight(1f)
                .fillMaxWidth()

        ) {


            items(messages) { message ->


                Card(

                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {


                    Text(

                        text =
                        if(message.fromUser)

                            "You:\n${message.text}"

                        else

                            "AI Teacher:\n${message.text}",


                        modifier =
                        Modifier.padding(12.dp)

                    )

                }


            }

        }





        Row(
            modifier =
            Modifier.fillMaxWidth()
        ) {


            OutlinedTextField(

                value = question,

                onValueChange = {
                    question = it
                },

                label = {
                    Text("Ask your teacher")
                },

                modifier =
                Modifier.weight(1f)

            )



            Spacer(
                Modifier.width(8.dp)
            )



            Button(

                onClick = {


                    if(question.isNotBlank()) {


                        val userQuestion =
                            question


                        messages.add(

                            AIMessage(

                                text = userQuestion,

                                fromUser = true,

                                mode = selectedMode

                            )

                        )


                        question = ""



                        scope.launch {


                            val answer =


                                if(selectedMode == AIMode.EXAM) {


                                    val examiner =
                                        AIExaminerService()



                                    val examQuestion =
                                        examiner.generateQuestion(

                                            selectedSubject,

                                            selectedTopic

                                        )



                                    """
                                    📝 ZIMSEC EXAM QUESTION
                                    
                                    Subject:
                                    ${examQuestion.subject}
                                    
                                    Topic:
                                    ${examQuestion.topic}
                                    
                                    Marks:
                                    ${examQuestion.marks}
                                    
                                    Difficulty:
                                    ${examQuestion.difficulty}
                                    
                                    Question:
                                    ${examQuestion.questionText}
                                    
                                    Answer when ready.
                                    """.trimIndent()



                                } else {


                                    ai.askAI(

                                        question =
                                        userQuestion,


                                        context =
                                        context,


                                        mode =
                                        selectedMode

                                    )

                                }




                            messages.add(

                                AIMessage(

                                    text = answer,

                                    fromUser = false,

                                    mode = selectedMode

                                )

                            )


                        }


                    }


                }

            ) {

                Text("Send")

            }


        }


    }


}
