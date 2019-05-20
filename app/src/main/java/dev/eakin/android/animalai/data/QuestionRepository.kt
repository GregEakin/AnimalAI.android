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