package com.example.weatherforecast.domain.model

data class CityInfo(
    val pictureBytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CityInfo

        if (!pictureBytes.contentEquals(other.pictureBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        return pictureBytes.contentHashCode()
    }
}