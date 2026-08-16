package com.zimstudy.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
        val difference = it.examDateMillis - System.currentTimeMillis()
        TimeUnit.MILLISECONDS.toDays(difference)
            .coerceAtLeast(0)
    }

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
            "Welcome, ${profile?.name ?: "Student"}",
            style = MaterialTheme.typography.bodyLarge
        )


        Spacer(modifier = Modifier.height(16.dp))


        // Exam countdown

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    "🔥 EXAM COUNTDOWN",
                    fontWeight = FontWeight.Bold
                )

                if(daysToExam != null){

                    Text(
                        "$daysToExam DAYS REMAINING",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        "${nextExam?.subjectName} - Paper ${nextExam?.paperNumber}"
                    )

                } else {

                    Text("Add your exam timetable")

                }

            }
        }


        Spacer(modifier = Modifier.height(15.dp))


        // Today's mission

        Card(
            modifier = Modifier.fillMaxWidth()
        ){

            Column(
                modifier = Modifier.padding(16.dp)
            ){

                Text(
                    "🎯 TODAY'S MISSION",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Complete your highest priority topic"
                )

                Text(
                    "Focus session: 18:00 - 19:00"
                )

            }

        }


        Spacer(modifier = Modifier.height(15.dp))


        // Mastery placeholder

        Card(
            modifier = Modifier.fillMaxWidth()
        ){

            Column(
                modifier = Modifier.padding(16.dp)
            ){

                Text(
                    "📊 ESTIMATED MASTERY",
                    fontWeight = FontWeight.Bold
                )

                Text("Biology     --%")
                Text("Physics     --%")
                Text("Mathematics --%")

                Text(
                    "Complete assessments to calculate mastery"
                )

            }

        }


        Spacer(modifier = Modifier.height(15.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Text(
                "📚 SUBJECTS",
                fontWeight = FontWeight.Bold
            )


            TextButton(
                onClick = onOpenSubjects
            ){
                Text("Manage")
            }

        }


        LazyColumn(
            modifier = Modifier.weight(1f)
        ){

            items(subjects){ subject ->


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ){

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){

                        Column{

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
                        ){

                            Text("START")

                        }

                    }

                }


            }

        }


    }

}
