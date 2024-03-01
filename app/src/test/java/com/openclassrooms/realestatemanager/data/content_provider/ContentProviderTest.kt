package com.openclassrooms.realestatemanager.data.content_provider

import android.database.Cursor
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
    fun query_returns_cursor_based_on_URI() {
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
            resultCursor?.setNotificationUri(any(), any())
        }

        Assert.assertEquals(fakeCursor, resultCursor)

    }
}