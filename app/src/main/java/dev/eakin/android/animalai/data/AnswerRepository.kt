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

class AnimalRepository private constructor(
    private val animalDao: AnimalDao
) {

    suspend fun getAllAnimals() {
        withContext(IO) {
            animalDao.getAllAnimals()
        }
    }

    suspend fun getAnimal(name: String) {
        withContext(IO) {
            animalDao.getAnimal(name)
        }
    }
    suspend fun getAnimal(parentId: Long, answer: Boolean) {
        withContext(IO) {
            animalDao.getAnimal(parentId, answer)
        }
    }

    suspend fun createAnimal(animalId: Long, name: String, parentId: Long, answer: Boolean) {
        withContext(IO) {
            val animal = Animal(animalId, name, parentId, answer)
            animalDao.insert(animal)
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AnimalRepository? = null

        fun getInstance(gardenPlantingDao: AnimalDao) =
            instance ?: synchronized(this) {
                instance ?: AnimalRepository(gardenPlantingDao).also { instance = it }
            }
    }
}