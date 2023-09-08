package com.example.sqlrecap.db
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sqlrecap.dao.DepenseDao
import com.example.sqlrecap.dao.DepenseTypeDao
import com.example.sqlrecap.dao.TypeDao
import com.example.sqlrecap.model.Depense
import com.example.sqlrecap.model.DepenseType
import com.example.sqlrecap.model.DepenseWithType
import com.example.sqlrecap.model.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.Date

@Database(entities = arrayOf(Depense::class, DepenseType::class, Type::class), version = 1)
abstract class DepenseDatabase : RoomDatabase() {
    abstract fun depenseDao(): DepenseDao
    abstract fun typeDao(): TypeDao
    abstract fun depenseTypeDao(): DepenseTypeDao

    companion object {

        @Volatile
        private var sharedInstance: DepenseDatabase? = null

        fun getDB(context: Context) : DepenseDatabase {
            if (sharedInstance != null) return sharedInstance!!
            synchronized(this) {
                sharedInstance = Room
                    .databaseBuilder(context, DepenseDatabase::class.java, "Depense.db")
                    .fallbackToDestructiveMigration()
                    .build()
                return sharedInstance!!
            }
        }
    }
}


class DepenseRepository {
    companion object {
        var depenseDatabase: DepenseDatabase? = null

        fun initializeDB(context: Context) : DepenseDatabase {
            val db = DepenseDatabase.getDB(context)
            CoroutineScope(IO).launch {
                val genres = depenseDatabase!!.typeDao().getAllGenre()
                if(genres.isNullOrEmpty()) {
                    depenseDatabase!!.typeDao().insert(Type(name = "Autre"))
                    depenseDatabase!!.typeDao().insert(Type(name = "Tax"))
                    depenseDatabase!!.typeDao().insert(Type(name = "Energie"))
                    depenseDatabase!!.typeDao().insert(Type(name = "Nouriture"))
                    depenseDatabase!!.typeDao().insert(Type(name = "Sortie"))
                    depenseDatabase!!.typeDao().insert(Type(name = "Maison"))
                }
            }
            return db
        }

        fun insertDepense(
            context: Context,
            nom: String,
            prix: Long,
            date : String,
            selectedType: Type
        ) {
            if(depenseDatabase == null) {
                depenseDatabase = initializeDB(context)
            }

            CoroutineScope(IO).launch {
                val depense = Depense(nom = nom, prix = prix, date = date)
                val depenseId = depenseDatabase!!.depenseDao().insert(depense)
                depenseDatabase!!.depenseTypeDao().insert(DepenseType(depenseId = depenseId, typeId = selectedType.typeId))

            }
        }

        fun insertType(context: Context, name: String) {
            if(depenseDatabase == null) {
                depenseDatabase = initializeDB(context)
            }
            CoroutineScope(IO).launch {
                val genre = Type(name = name)
                depenseDatabase!!.typeDao().insert(genre)
            }
        }

        fun getDepenseDetails(context: Context, id: Long) : LiveData<DepenseWithType>? {
            if(depenseDatabase == null) {
                depenseDatabase = initializeDB(context)
            }
            return depenseDatabase!!.depenseDao().findWithTypeById(id)
        }

        fun getAllDepenses(context: Context): LiveData<List<DepenseWithType>> {
            if(depenseDatabase == null) {
                depenseDatabase = initializeDB(context)
            }
            return depenseDatabase!!.depenseDao().getAll()
        }

        fun getAllType(context: Context): LiveData<List<Type>> {
            if(depenseDatabase == null) {
                depenseDatabase = initializeDB(context)
            }
            return depenseDatabase!!.typeDao().getAll()
        }

        fun deleteDepense(context: Context, movieId: Long) {
            if(depenseDatabase == null) {
                depenseDatabase = initializeDB(context)
            }
            depenseDatabase?.let { db ->
                CoroutineScope(IO).launch {
                    db.depenseDao().deleteMovie(movieId)
                }
            }
        }

    }
}