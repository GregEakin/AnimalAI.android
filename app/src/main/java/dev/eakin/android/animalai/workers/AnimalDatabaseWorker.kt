package dev.eakin.android.animalai.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dev.eakin.android.animalai.data.Animal
import dev.eakin.android.animalai.data.AppDatabase
import dev.eakin.android.animalai.data.Question
import dev.eakin.android.animalai.utilities.ANIMAL_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class AnimalDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG by lazy { AnimalDatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result = coroutineScope {

        try {
            applicationContext.assets.open(ANIMAL_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val questionType = object : TypeToken<List<Question>>() {}.type
                    val questionList: List<Question> = Gson().fromJson(jsonReader, questionType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.questionDao().insertAll(questionList)

                    Result.success()
                }
            }
            applicationContext.assets.open(ANIMAL_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val animalType = object : TypeToken<List<Animal>>() {}.type
                    val animalList: List<Animal> = Gson().fromJson(jsonReader, animalType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.animalDao().insertAll(animalList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}