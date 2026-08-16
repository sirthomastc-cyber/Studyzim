package com.zimstudy.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zimstudy.app.StudyViewModel
import java.util.concurrent.TimeUnit


@Composable
fun DashboardScreen(
    viewModel: StudyViewModel,
    onOpenSubjects: () -> Unit,
    onStartTimer: (subject: String, topic: String) -> Unit
) {

    val profile by viewModel.profile.collectAsState()
    val subjects by viewModel.subjects.collectAsState()
    val exams by viewModel.exams.collectAsState()


    val nextExam = exams.minByOrNull { it.examDateMillis }


    val daysToExam = nextExam?.let {

        val diff =
            it.examDateMillis - System.currentTimeMillis()

        TimeUnit.MILLISECONDS
            .toDays(diff)
            .coerceAtLeast(0)

    }



    // LIVE MASTERY DATA

    val biologyMastery =
        viewModel.getMastery("Biology")


    val physicsMastery =
        viewModel.getMastery("Physics")


    val mathematicsMastery =
        viewModel.getMastery("Mathematics")



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {


        Text(
            "ZIMStudy AI",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )


        Text(
            "Welcome back, ${profile?.name ?: "Student"}"
        )



        Spacer(
            Modifier.height(16.dp)
        )



        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                Modifier.padding(16.dp)
            ) {

                Text(
                    "🔥 EXAM COUNTDOWN",
                    fontWeight = FontWeight.Bold
                )


                if(nextExam != null) {

                    Text(
                        "$daysToExam DAYS REMAINING",
                        style = MaterialTheme.typography.headlineSmall
                    )


                    Text(
                        "${nextExam.subjectName} - Paper ${nextExam.paperNumber}"
                    )

                } else {

                    Text(
                        "Add your exam timetable"
                    )

                }

            }

        }



        Spacer(
            Modifier.height(15.dp)
        )



        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                Modifier.padding(16.dp)
            ) {

                Text(
                    "🎯 TODAY'S MISSION",
                    fontWeight = FontWeight.Bold
                )


                Text(
                    "Complete your highest priority topic"
                )


                Text(
                    "Recommended time: 18:00 - 19:00"
                )

            }

        }



        Spacer(
            Modifier.height(15.dp)
        )



        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                Modifier.padding(16.dp)
            ) {

                Text(
                    "📊 ESTIMATED MASTERY",
                    fontWeight = FontWeight.Bold
                )


                Text(
                    "Biology       ${biologyMastery.toInt()}%"
                )


                Text(
                    "Physics       ${physicsMastery.toInt()}%"
                )


                Text(
                    "Mathematics   ${mathematicsMastery.toInt()}%"
                )


                Text(
                    "Based on quizzes, past papers and consistency"
                )

            }

        }



        Spacer(
            Modifier.height(15.dp)
        )



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                "📚 SUBJECTS",
                fontWeight = FontWeight.Bold
            )


            TextButton(
                onClick = onOpenSubjects
            ) {

                Text("Manage")

            }

        }



        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {


            items(subjects) { subject ->


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Column {


                            Text(
                                subject.name,
                                fontWeight = FontWeight.Bold
                            )


                            Text(
                                "Target: ${subject.targetGrade}"
                            )

                        }



                        Button(
                            onClick = {

                                onStartTimer(
                                    subject.name,
                                    "Priority revision"
                                )

                            }
                        ) {

                            Text("START")

                        }

                    }

                }


            }


        }


    }


}
