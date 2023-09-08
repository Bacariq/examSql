package com.example.sqlrecap.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sqlrecap.model.Depense
import com.example.sqlrecap.model.DepenseType
import com.example.sqlrecap.model.DepenseWithType
import com.example.sqlrecap.model.Type


@Dao
interface TypeDao {

    @Query("SELECT * FROM Type WHERE typeId = :typeId LIMIT 1")
    fun findById(typeId: Long): LiveData<Type>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(type: Type)

    @Query("SELECT * FROM Type")
    fun getAll(): LiveData<List<Type>>

    @Query("SELECT * FROM Type")
    suspend fun getAllGenre(): List<Type>
}

@Dao
interface DepenseTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(depenseType: DepenseType)

    @Query("SELECT * FROM DepenseType")
    fun getAll(): LiveData<List<DepenseType>>
}

@Dao
interface DepenseDao {

    @Query("SELECT * FROM Depense WHERE depenseId = :bookId LIMIT 1")
    fun findById(bookId: Long): LiveData<Depense>

    @Query("SELECT * FROM Depense WHERE depenseId = :bookId LIMIT 1")
    fun findWithTypeById(bookId: Long): LiveData<DepenseWithType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(depense: Depense): Long

    @Query("SELECT * FROM Depense")
    fun getAll(): LiveData<List<DepenseWithType>>

    @Query("DELETE FROM Depense WHERE depenseId = :depenseId")
    suspend fun deleteMovie(depenseId: Long)


}