package com.zimstudy.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zimstudy.app.ai.AIMode
import com.zimstudy.app.ai.AIMessage
import com.zimstudy.app.ai.OfflineAIService
import kotlinx.coroutines.launch


@Composable
fun AITeacherScreen() {


    var question by remember {
        mutableStateOf("")
    }


    var selectedMode by remember {
        mutableStateOf(AIMode.TEACH)
    }



    val messages = remember {
        mutableStateListOf<AIMessage>()
    }



    val scope = rememberCoroutineScope()


    val ai = OfflineAIService()



    Column(
        modifier = Modifier
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
            "Choose teaching mode:"
        )



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Button(
                onClick = {
                    selectedMode = AIMode.TEACH
                }
            ){
                Text("Teach")
            }


            Button(
                onClick = {
                    selectedMode = AIMode.EXAM
                }
            ){
                Text("Exam")
            }


            Button(
                onClick = {
                    selectedMode = AIMode.EXPLAIN_MISTAKE
                }
            ){
                Text("Mistake")
            }

        }



        Spacer(
            Modifier.height(10.dp)
        )



        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ){


            items(messages){ message ->


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ){

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
            modifier = Modifier.fillMaxWidth()
        ){



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


                    if(question.isNotBlank()){


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


                            val response =
                                ai.askAI(
                                    userQuestion
                                )



                            messages.add(

                                AIMessage(
                                    text = response,
                                    fromUser = false,
                                    mode = selectedMode
                                )

                            )


                        }


                    }


                }

            ){

                Text("Send")

            }


        }


    }


}
