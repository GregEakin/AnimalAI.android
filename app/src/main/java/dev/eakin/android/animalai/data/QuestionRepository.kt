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

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class QuestionRepository private constructor(
    private val questionDao: QuestionDao
) {

    suspend fun getAllQuestions() {
        withContext(IO) {
            questionDao.getAllQuestions()
        }
    }

    suspend fun getQuestion() {
        withContext(IO) {
            questionDao.getQuestion()
        }
    }

    suspend fun getQuestion(parentId: Long, answer: Boolean) {
        withContext(IO) {
            questionDao.getQuestion(parentId, answer)
        }
    }

    suspend fun createQuestion(questionId: Long, question: String, parentId: Long, answer: Boolean) {
        withContext(IO) {
            val q = Question(questionId, question, parentId, answer)
            questionDao.insert(q)
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: QuestionRepository? = null

        fun getInstance(questionDao: QuestionDao) =
            instance ?: synchronized(this) {
                instance ?: QuestionRepository(questionDao).also { instance = it }
            }
    }
}