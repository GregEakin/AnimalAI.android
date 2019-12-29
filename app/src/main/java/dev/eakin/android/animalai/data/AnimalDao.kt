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