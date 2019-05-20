package dev.eakin.android.animalai.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimalDao {

    @Query("SELECT * from animals ORDER BY name ASC")
    fun getAllAnimals(): LiveData<List<Animal>>

    @Query("SELECT * from animals WHERE name = :name")
    fun getAnimal(name: String): LiveData<Animal>

    @Query("SELECT * from animals WHERE parentId = :parentId AND answer = :answer")
    fun getAnimal(parentId: Long, answer: Boolean): LiveData<Animal>

    @Insert
    suspend fun insert(animal: Animal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(animals: List<Animal>)

    @Query("DELETE FROM animals")
    suspend fun deleteAll()
}