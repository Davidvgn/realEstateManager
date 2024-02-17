package com.openclassrooms.realestatemanager.data.content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
import com.openclassrooms.realestatemanager.data.pictures.PicturesDao
import com.openclassrooms.realestatemanager.data.real_estates.RealEstateDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlin.math.PI

class ContentProvider : ContentProvider() {

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, "realEstate", REAL_ESTATES)
        addURI(AUTHORITY, "pictures", PICTURES)
    }

    companion object {
        private const val AUTHORITY =
            "com.openclassrooms.realestatemanager.data.content_provider.ContentProvider"
        private const val TABLE_NAME = "RealEstate_database"

        private const val REAL_ESTATES = 1
        private const val PICTURES = 2
    }


    @Inject
    lateinit var entryPoint: ContentProviderEntryPoint
    lateinit var realEstateDao: RealEstateDao
    lateinit var picturesDao: PicturesDao

    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext
        val hiltEntryPoint =
            appContext?.let {
                EntryPointAccessors.fromApplication(
                    it,
                    ContentProviderEntryPoint::class.java
                )
            }
        if (hiltEntryPoint != null) {
            realEstateDao = hiltEntryPoint.getRealEstateDao()
        }
        if (hiltEntryPoint != null) {
            picturesDao = hiltEntryPoint.getPicturesDao()
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return try {
            when (uriMatcher.match(uri)) {
                REAL_ESTATES -> {
                    realEstateDao.getAllPropertiesWithCursor()
                }

                PICTURES -> {
                    picturesDao.getAllPicturesWithCursor()
                }

                else -> {
                    throw IllegalArgumentException("URI unknown: $uri")
                }

            }
        } catch (e: SQLiteException) {
            Log.e("ContentProvider", "Error querying URI: $uri", e)
            MatrixCursor(arrayOf("error_message")).apply {
                addRow(arrayOf(e.message ?: "Unknown error!"))
            }
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")


    }

    @EntryPoint  // runtime dependency graph
    @InstallIn(SingletonComponent::class)
    interface ContentProviderEntryPoint {
        fun getRealEstateDao(): RealEstateDao
        fun getPicturesDao(): PicturesDao
    }


}