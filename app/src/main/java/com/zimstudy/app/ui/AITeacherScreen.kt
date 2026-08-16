package com.zimstudy.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zimstudy.app.ai.OfflineAIService
import kotlinx.coroutines.launch


@Composable
fun AITeacherScreen() {


    var question by remember {
        mutableStateOf("")
    }


    var answer by remember {
        mutableStateOf(
            "Ask me anything about your studies."
        )
    }


    val scope = rememberCoroutineScope()

    val ai =
        OfflineAIService()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {


        Text(
            "🤖 ZIMStudy AI Teacher",
            style = MaterialTheme.typography.headlineSmall
        )


        Spacer(
            Modifier.height(20.dp)
        )



        OutlinedTextField(
            value = question,
            onValueChange = {
                question = it
            },
            label = {
                Text("Ask your question")
            },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(
            Modifier.height(10.dp)
        )


        Button(

            onClick = {

                scope.launch {

                    answer =
                        ai.askAI(question)

                }

            }

        ){

            Text("Ask AI")

        }



        Spacer(
            Modifier.height(20.dp)
        )


        Card(
            modifier = Modifier.fillMaxWidth()
        ){

            Text(
                answer,
                modifier = Modifier.padding(16.dp)
            )

        }

    }

}
