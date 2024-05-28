package com.nat.winsome_assessment.application.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nat.winsome_assessment.application.data.local.db.dao.MoviesDao
import com.nat.winsome_assessment.application.data.local.db.entity.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1 , exportSchema = false)
@TypeConverters(DatabaseTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun dao(): MoviesDao
}