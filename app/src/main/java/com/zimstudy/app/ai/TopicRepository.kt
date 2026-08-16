package com.zimstudy.app.ai


object TopicRepository {


    fun getTopics(subject: String): List<Topic> {


        return when(subject){


            "Biology" -> listOf(

                Topic("Cell Structure"),
                Topic("Enzymes"),
                Topic("Genetics"),
                Topic("Ecology")

            )


            "Physics" -> listOf(

                Topic("Electricity"),
                Topic("Forces"),
                Topic("Energy"),
                Topic("Waves")

            )


            "Mathematics" -> listOf(

                Topic("Algebra"),
                Topic("Functions"),
                Topic("Geometry"),
                Topic("Statistics")

            )


            "Chemistry" -> listOf(

                Topic("Atoms"),
                Topic("Bonding"),
                Topic("Acids and Bases"),
                Topic("Organic Chemistry")

            )


            else -> listOf(

                Topic("General Revision")

            )


        }

    }


}
