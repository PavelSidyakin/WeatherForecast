package com.example.weatherforecast.domain.model

data class CityInfo(
    val pictureBytes: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CityInfo

        if (pictureBytes != null) {
            if (other.pictureBytes == null) return false
            if (!pictureBytes.contentEquals(other.pictureBytes)) return false
        } else if (other.pictureBytes != null) return false

        return true
    }

    override fun hashCode(): Int {
        return pictureBytes?.contentHashCode() ?: 0
    }
}