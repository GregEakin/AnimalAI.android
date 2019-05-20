package dev.eakin.android.animalai.data

import androidx.room.Embedded
import androidx.room.Relation

class AnimalsParent {

    @Embedded
    lateinit var animal: Animal

    @Relation(parentColumn = "id", entityColumn = "parentId")
    var parents: List<Question> = arrayListOf()
}