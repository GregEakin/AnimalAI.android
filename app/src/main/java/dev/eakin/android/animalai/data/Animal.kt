package dev.eakin.android.animalai.data

import androidx.annotation.NonNull
import androidx.room.*

@Entity(
    tableName = "animals",
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
            name = "parentKey2",
            unique = true
        ),
        Index(
            value = ["name"],
            name = "nameKey",
            unique = true
        )
    ]
)
data class Animal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val animalId: Long = 0L,

    @NonNull
    val name: String,

    @NonNull
    @ColumnInfo(name = "parentId")
    val parentId: Long,

    @NonNull
    val answer: Boolean
)

