package com.openclassrooms.realestatemanager.data.content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
import androidx.room.Room
import com.openclassrooms.realestatemanager.data.AppDatabase
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
    private lateinit var appDatabase: AppDatabase
    private lateinit var realEstateDao: RealEstateDao
    private lateinit var picturesDao: PicturesDao


    companion object {
        private const val AUTHORITY =
            "com.openclassrooms.realestatemanager.data.content_provider.ContentProvider"
        private const val TABLE_NAME = "RealEstate_database"
        private const val MIME_TYPE_PREFIX = "vnd.android.cursor.dir/vnd."

        private const val REAL_ESTATES = 1
        private const val PICTURES = 2
    }

    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext ?: throw IllegalStateException("Context is null")

        appDatabase = Room.databaseBuilder(appContext, AppDatabase::class.java, TABLE_NAME).build()
        realEstateDao = appDatabase.getRealEstateDao()
        picturesDao = appDatabase.getPicturesDao()
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

    override fun getType(uri: Uri): String {

        return when (uriMatcher.match(uri)) {
            REAL_ESTATES -> "$MIME_TYPE_PREFIX$AUTHORITY.$TABLE_NAME"
            PICTURES -> "$MIME_TYPE_PREFIX$AUTHORITY.$TABLE_NAME"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }    }

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

}