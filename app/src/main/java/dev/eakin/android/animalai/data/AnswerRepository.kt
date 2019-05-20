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