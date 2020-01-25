package com.example.weatherforecast.data

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.domain.WeatherOfflineRepository
import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import java.util.Date
import javax.inject.Inject

class WeatherOfflineRepositoryImpl
    @Inject
    constructor(
        private val applicationProvider: ApplicationProvider
    ) : WeatherOfflineRepository {

    private val picturesDir: String
        get() {
            return applicationProvider.applicationContext.getDir("pictures", Context.MODE_PRIVATE).absolutePath
        }

    override suspend fun requestAllCitiesInfo(): Map<String, CityInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun requestCityWeather(cityName: String): CityWeather {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveCitiesInfo(citiesWeather: Map<String, CityInfo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveCitiesWeather(citiesWeather: Map<String, CityWeather>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clearWeatherOlderThan(date: Date) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    @Dao
    private interface WeatherDao {

//        @Query("SELECT dbcityinfo.* " +
//                "FROM dbcityinfo " +
//                "LEFT JOIN dbcity " +
//                "on dbcity.name = dbcityinfo.city_name")
//        suspend fun dbRequestAllCitiesInfo(): List<DbCityInfo>

        @Query(
            "SELECT * " +
            "FROM dbcityinfo")
        suspend fun dbRequestAllCitiesInfo(): List<DbCityInfo>

        @Query(
            "SELECT * " +
                    "FROM dbcityweather")
        suspend fun dbRequestCityWeather(): List<DbCityWeather>

    }

    @Entity
    private data class DbCity(
        @PrimaryKey
        @ColumnInfo(name = "name")
        val name: String
    )

    @Entity(foreignKeys = [ForeignKey(entity = DbCity::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("city_name"),
        onDelete = ForeignKey.CASCADE)])
    private data class DbCityInfo(
        @ColumnInfo(name = "city_name")
        val cityName: String,

        @ColumnInfo(name = "picture_file_path")
        val pictureFilePath: String?
    )

    @Entity(foreignKeys = [ForeignKey(entity = DbCity::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("city_name"),
        onDelete = ForeignKey.CASCADE)])
    private data class DbCityWeather(
        @ColumnInfo(name = "city_name")
        val cityName: String,

        @ColumnInfo(name = "time")
        val time: Long,

        @ColumnInfo(name = "temperature")
        val temperature: Float
    )

    companion object {
        private const val TAG = "WeatherOfflineRepo"
    }
}