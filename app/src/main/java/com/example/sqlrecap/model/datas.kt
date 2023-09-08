package com.example.sqlrecap.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity
data class Depense(
    @PrimaryKey(autoGenerate = true)
    var depenseId: Long = 0,
    var nom: String,
    var prix: Long,
    var date : String
)

@Entity(primaryKeys = ["depenseId", "typeId"])
data class DepenseType(
    var depenseId: Long,
    var typeId: Long
)

data class DepenseWithType(
    @Embedded
    val depense: Depense,
    @Relation(
        parentColumn = "depenseId",
        entityColumn = "typeId",
        associateBy = Junction(DepenseType::class)
    )
    val types: Type
)

@Entity
data class Type(
    @PrimaryKey(autoGenerate = true)
    var typeId: Long = 0,
    var name: String
)
