/*
 * Copyright 2019 Greg Eakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
