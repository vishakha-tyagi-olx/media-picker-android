package com.mediapicker.gallery

import android.content.Context
import androidx.annotation.LayoutRes
import com.mediapicker.gallery.domain.contract.GalleryCommunicatorDefaultImpl
import com.mediapicker.gallery.domain.contract.IGalleryCommunicator
import com.mediapicker.gallery.domain.entity.CarousalConfig
import com.mediapicker.gallery.domain.entity.GalleryLabels
import com.mediapicker.gallery.domain.entity.PhotoTag
import com.mediapicker.gallery.domain.entity.Validation

class GalleryConfig(
    val applicationContext: Context,
    val clientAuthority: String,
    var galleryCommunicator: IGalleryCommunicator?,
    val shouldUsePhotoCamera: Boolean,
    val shouldUseVideoCamera: Boolean,
    val needToShowCover: PhotoTag,
    val photoViewPlaceHolder: Int,
    val showPreviewCarousal: CarousalConfig,
    val typeOfMediaSupported: MediaType,
    val validation: Validation,
    val photoTag: PhotoTag,
    val mediaScanningCriteria: MediaScanningCriteria,
    val textAllCaps: Boolean,
    val galleryLabels: GalleryLabels
) {


    fun shouldOnlyValidatePhoto() = typeOfMediaSupported == MediaType.PhotoWithFolderOnly || typeOfMediaSupported == MediaType.PhotoOnly || typeOfMediaSupported == MediaType.PhotoWithoutCameraFolderOnly

    fun isGalleryInitialize() = applicationContext != null && clientAuthority != null


    class GalleryNotInitilizedException(message: String = "Please initialize gallery with application and client authority") : Exception(message)

    class GalleryConfigBuilder(
        private val applicationContext: Context,
        private val clientAuthority: String,
        private val galleryCommunicator: IGalleryCommunicator? = GalleryCommunicatorDefaultImpl()
    ) {
        private var shouldUsePhotoCamera: Boolean = false
        private var shouldUseVideoCamera: Boolean = false
        private var textAllCaps: Boolean = true
        private var needToShowCover: PhotoTag = PhotoTag()
        private var showPreviewCarousal: CarousalConfig = CarousalConfig()


        @LayoutRes
        private var photoViewPlaceHolder: Int = 0
        private var typeOfMediaSupported: MediaType = MediaType.PhotoWithFolderAndVideo
        private lateinit var validation: Validation
        private  var photoTag: PhotoTag=PhotoTag()
        private var mediaScanningCriteria = MediaScanningCriteria()
        private var galleryLabels = GalleryLabels()

        fun textAllCaps(textAllCaps: Boolean) = apply { this.textAllCaps = textAllCaps }
        fun useMyPhotoCamera(shouldUseMyCamera: Boolean) = apply { this.shouldUsePhotoCamera = shouldUseMyCamera }
        fun useMyVideoCamera(shouldUseMyCamera: Boolean) = apply { this.shouldUseVideoCamera = shouldUseMyCamera }
        fun needToShowCover(needToShowCover: PhotoTag) = apply { this.needToShowCover = needToShowCover }
        fun needToShowPreviewCarousal(showPreviewCarousal: CarousalConfig) = apply { this.showPreviewCarousal = showPreviewCarousal }
        fun photoViewPlaceHolder(layout: Int) = apply { this.photoViewPlaceHolder = layout }
        fun typeOfMediaSupported(mediaType: MediaType) = apply { this.typeOfMediaSupported = mediaType }
        fun setGalleryLabels(galleryLabels: GalleryLabels) = apply { this.galleryLabels = galleryLabels }

        fun validation(validation: Validation): GalleryConfigBuilder {
            this.validation = validation
            return this
        }
        fun photoTag(photoTag: PhotoTag):GalleryConfigBuilder{
            this.photoTag=photoTag
            return this
        }

        fun mediaScanningCriteria(mediaScanningCriteria: MediaScanningCriteria) = apply { this.mediaScanningCriteria = mediaScanningCriteria }

        fun build() = GalleryConfig(
            applicationContext,
            clientAuthority,
            galleryCommunicator,
            shouldUsePhotoCamera,
            shouldUseVideoCamera,
            needToShowCover,
            photoViewPlaceHolder,
            showPreviewCarousal,
            typeOfMediaSupported,
            validation,
            photoTag,
            mediaScanningCriteria,
            textAllCaps,
            galleryLabels
        )

    }

    sealed class MediaType {
        object PhotoOnly : MediaType()
        object PhotoWithVideo : MediaType()
        object PhotoWithFolderAndVideo : MediaType()
        object PhotoWithFolderOnly : MediaType()
        object PhotoWithoutCameraFolderOnly : MediaType()
    }

    data class MediaScanningCriteria(val photoBrowseQuery: String = "", val videoBrowseQuery: String = "") {

        fun hasCustomQueryForPhoto() = photoBrowseQuery.isNotEmpty()

        fun hasCustomQueryForVideo() = videoBrowseQuery.isNotEmpty()
    }

}


