package com.openclassrooms.realestatemanager.data.content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.openclassrooms.realestatemanager.data.pictures.PicturesDao
import com.openclassrooms.realestatemanager.data.real_estates.RealEstateDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class ContentProvider : ContentProvider() {
    private val uriMatcher =
        UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "realEstate", REAL_ESTATES)
            addURI(AUTHORITY, "pictures", PICTURES)
        }

    lateinit var realEstateDao: RealEstateDao
    lateinit var picturesDao: PicturesDao

    companion object {
        const val AUTHORITY =
            "com.openclassrooms.realestatemanager.data.content_provider.ContentProvider"
        private const val TABLE_NAME = "RealEstate_database"
        private const val MIME_TYPE_PREFIX = "vnd.android.cursor.dir/vnd."

        private const val REAL_ESTATES = 1
        private const val PICTURES = 2
    }

    override fun onCreate(): Boolean {
        val appContext =
            context?.applicationContext ?: throw IllegalStateException("Context is null")
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(appContext, ContentProviderEntryPoint::class.java)

        realEstateDao = hiltEntryPoint.getRealEstateDao()
        picturesDao = hiltEntryPoint.getPicturesDao()

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?,
    ): Cursor? {
        val match = uriMatcher.match(uri)
        return try {
            when (match) {
                REAL_ESTATES -> realEstateDao.getAllPropertiesWithCursor()
                PICTURES -> picturesDao.getAllPicturesWithCursor()
                else -> throw IllegalArgumentException("Unknown URI: $uri")
            }.apply {
                setNotificationUri(context?.contentResolver, uri)
            }
        } catch (e: Exception) {
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
        }
    }

    override fun insert(
        uri: Uri,
        values: ContentValues?,
    ): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?,
    ): Int {
        TODO("Not yet implemented")
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ContentProviderEntryPoint {
        fun getRealEstateDao(): RealEstateDao

        fun getPicturesDao(): PicturesDao
    }
}
