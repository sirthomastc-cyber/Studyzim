package com.zimstudy.app.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zimstudy.app.StudyViewModel
import com.zimstudy.app.ai.OfflineAIService


@Composable
fun AITeacherScreen(
    viewModel: StudyViewModel
) {


    var question by remember {
        mutableStateOf("")
    }


    var answer by remember {
        mutableStateOf(
            "Ask me anything about your subjects."
        )
    }



    val aiService =
        remember {
            OfflineAIService()
        }




    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)

    ) {



        Text(

            text = "🤖 ZIMStudy AI Teacher",

            style =
            MaterialTheme.typography.headlineMedium

        )



        Spacer(
            Modifier.height(20.dp)
        )



        Card(

            modifier =
            Modifier.fillMaxWidth()

        ) {


            Text(

                text = answer,

                modifier =
                Modifier.padding(16.dp)

            )

        }



        Spacer(
            Modifier.height(20.dp)
        )



        OutlinedTextField(

            value = question,

            onValueChange = {

                question = it

            },

            modifier =
            Modifier.fillMaxWidth(),

            label = {

                Text("Ask your question")

            }

        )



        Spacer(
            Modifier.height(15.dp)
        )



        Button(

            onClick = {


                answer =
                    aiService.askAI(
                        question
                    )


            },

            modifier =
            Modifier.fillMaxWidth()

        ) {


            Text(
                "Ask AI"
            )

        }


    }

}
