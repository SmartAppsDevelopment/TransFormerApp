package com.example.sofittasktranformer.db.repo

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sofittasktranformer.db.AppLocalDatabase
import com.example.sofittasktranformer.utils.ConstantsMembers.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDbRepo {

    @Singleton
    @Provides
    fun getRoomDbRef(@ApplicationContext context:Context)= Room.databaseBuilder(context, AppLocalDatabase::class.java, DATABASE_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .addCallback(
            object : RoomDatabase.Callback() {
            }
        )
        .build()
}