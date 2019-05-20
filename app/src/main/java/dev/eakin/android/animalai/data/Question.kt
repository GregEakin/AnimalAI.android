package dev.eakin.android.animalai.data

import androidx.annotation.NonNull
import androidx.room.*

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = ["id"],
            childColumns = ["parentId"]
        )
    ],
    indices = [
        Index(
            value = ["parentId", "answer"],
            name = "questionKey",
            unique = true
        )
    ]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val questionId: Long = 0L,

    @NonNull
    val data: String,

    @ColumnInfo(name = "parentId")
    val parentId: Long?,

    val answer: Boolean?
)
