package com.openclassrooms.realestatemanager.data.content_provider

import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.openclassrooms.realestatemanager.data.pictures.PicturesDao
import com.openclassrooms.realestatemanager.data.real_estates.RealEstateDao
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ContentProviderTest {

    private lateinit var contentProvider: ContentProvider
    private lateinit var realEstateDaoMock: RealEstateDao
    private lateinit var picturesDaoMock: PicturesDao

    @Before
    fun setup() {
        realEstateDaoMock = mockk()
        picturesDaoMock = mockk()


        contentProvider = ContentProvider().apply {
            realEstateDao = realEstateDaoMock
            picturesDao = picturesDaoMock
        }

        val fakeCursor: Cursor = mockk()
        justRun {
            fakeCursor.setNotificationUri(any(), any())
        }
        every { realEstateDaoMock.getAllPropertiesWithCursor() } returns fakeCursor
    }

    @Test
    fun query_returns_cursor_based_on_realestate_URI() {
        // Given
        val fakeCursor: Cursor = mockk()
        justRun {
            fakeCursor.setNotificationUri(
                any(),
                any()
            )
        }

        every { realEstateDaoMock.getAllPropertiesWithCursor() } returns fakeCursor

        // When
        val uri = Uri.parse("content://${ContentProvider.AUTHORITY}/realEstate")
        val resultCursor = contentProvider.query(uri, null, null, null, null)

        // Then
        verify { realEstateDaoMock.getAllPropertiesWithCursor() }
        verify {
            resultCursor?.setNotificationUri(any(), uri)
        }

        Assert.assertEquals(fakeCursor, resultCursor)

    }

    @Test
    fun query_returns_cursor_based_on_pictures_URI() {
        // Given
        val fakeCursor: Cursor = mockk()
        justRun {
            fakeCursor.setNotificationUri(
                any(),
                any()
            )
        }

        every { picturesDaoMock.getAllPicturesWithCursor() } returns fakeCursor

        // When
        val uri = Uri.parse("content://${ContentProvider.AUTHORITY}/pictures")
        val resultCursor = contentProvider.query(uri, null, null, null, null)

        // Then
        verify { picturesDaoMock.getAllPicturesWithCursor() }
        verify {
            resultCursor?.setNotificationUri(any(), uri)
        }

        Assert.assertEquals(fakeCursor, resultCursor)

    }

    @Test
    fun query_returns_exception_if_unknow_URI() {
        // Given
        val fakeCursor: Cursor = mockk()
        justRun {
            fakeCursor.setNotificationUri(
                any(),
                any()
            )
        }

        every { realEstateDaoMock.getAllPropertiesWithCursor() } returns fakeCursor

        // When
        val uri = Uri.parse("content://${ContentProvider.AUTHORITY}/toto")
        val resultCursor = contentProvider.query(uri, null, null, null, null)

        // Then
        Assert.assertEquals((resultCursor as MatrixCursor).columnNames[0], "error_message")

    }
}