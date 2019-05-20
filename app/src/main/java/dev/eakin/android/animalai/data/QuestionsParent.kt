package dev.eakin.android.animalai.data

import androidx.room.Embedded
import androidx.room.Relation

class QuestionsParent {

    @Embedded
    lateinit var question: Question

    @Relation(parentColumn = "id", entityColumn = "parentId")
    var parents: List<Question> = arrayListOf()
}