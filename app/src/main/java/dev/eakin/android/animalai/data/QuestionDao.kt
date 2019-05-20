package dev.eakin.android.animalai.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {

    @Query("SELECT * from questions ORDER BY data ASC")
    fun getAllQuestions(): LiveData<List<Question>>

    @Query("SELECT * from questions WHERE parentId IS NULL AND data IS NULL")
    fun getQuestion(): LiveData<Question>

    @Query("SELECT * from questions WHERE parentId = :parentId AND answer = :answer")
    fun getQuestion(parentId: Long, answer: Boolean): LiveData<Question>

    @Insert
    suspend fun insert(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: List<Question>)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()
}