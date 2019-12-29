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