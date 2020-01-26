package com.example.weatherforecast.data

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecast.common.ApplicationProvider
import com.example.weatherforecast.domain.WeatherOfflineRepository
import com.example.weatherforecast.domain.model.CityInfo
import com.example.weatherforecast.domain.model.CityWeather
import com.example.weatherforecast.domain.model.data.WeatherOfflineSaveResultCode
import com.example.weatherforecast.utils.DispatcherProvider
import com.example.weatherforecast.utils.logs.log
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Date
import javax.inject.Inject

class WeatherOfflineRepositoryImpl
    @Inject
    constructor(
        private val applicationProvider: ApplicationProvider,
        private val dispatcherProvider: DispatcherProvider
    ) : WeatherOfflineRepository {

    private val picturesDir: String by lazy {
        applicationProvider.applicationContext.getDir("pictures", Context.MODE_PRIVATE).absolutePath
    }

    private val weatherDb: WeatherDatabase by lazy {
        Room.databaseBuilder(
            applicationProvider.applicationContext,
            WeatherDatabase::class.java, "db_weather_database"
        ).build()
    }

    override suspend fun requestAllCitiesInfo(): Map<String, CityInfo> {
        return weatherDb.weatherDao().dbRequestAllCitiesInfo().associateBy({ dbCityInfo ->
            dbCityInfo.cityName
        }, { dbCityInfo ->
            if (!dbCityInfo.pictureFilePath.isNullOrBlank()) {
                withContext(dispatcherProvider.io()) {
                    try {
                        CityInfo(File(dbCityInfo.pictureFilePath).readBytes())
                    } catch (exception: Exception) {
                        CityInfo(null)
                    }
                }
            } else {
                CityInfo(null)
            }
        })
    }

    override suspend fun requestCityWeather(cityName: String): Map<Date, Float> {
        return weatherDb.weatherDao().dbRequestCityWeather(cityName).associateBy({ dbCityWeather ->
            Date(dbCityWeather.time)
        }, { dbCityWeather ->
            dbCityWeather.temperature
        })
    }

    override suspend fun saveCitiesInfo(citiesInfo: Map<String, CityInfo>): WeatherOfflineSaveResultCode {
        val dbCitiesInfos: MutableList<DbCityInfo> = mutableListOf()

        citiesInfo.forEach { entry ->
            val cityName = entry.key
            val cityInfo = entry.value

            val picturePath = cityInfo.pictureBytes?.let {
                savePictureToFile(cityName, cityInfo.pictureBytes)
            }

            val dbCityInfo = DbCityInfo(cityName, picturePath?.absolutePath?:"")
            dbCitiesInfos.add(dbCityInfo)


            log { i(TAG, "WeatherOfflineRepositoryImpl.saveCitiesInfo(). $dbCityInfo") }
        }

        return insertToDbWithResult { dbInsertCitiesInfos(dbCitiesInfos) }
    }

    override suspend fun saveCitiesWeather(citiesWeather: Map<String, CityWeather>): WeatherOfflineSaveResultCode {
        val cdbCitiesWeather: List<DbCityWeather> = citiesWeather.toList()
            .map { entry ->
                val cityName = entry.first
                val cityWeather = entry.second
                cityWeather.dateToTemperatureMap.toList().map { dateTemperaturePair ->
                    DbCityWeather(cityName, dateTemperaturePair.first.time, dateTemperaturePair.second)
                }.also {
                        log { i(TAG, "WeatherOfflineRepositoryImpl.saveCitiesWeather(). $it") }
                    }
            }.flatten()

        return insertToDbWithResult { dbInsertCitiesWeather(cdbCitiesWeather) }
    }

    override suspend fun clearCitiesInfo() {
        withContext(dispatcherProvider.io()) {
            weatherDb.weatherDao().dbClearCitiesInfo()
            File(picturesDir).deleteRecursively()
        }
    }

    override suspend fun clearCitiesWeather() {
        weatherDb.weatherDao().dbClearCitiesWeather()
    }

    override suspend fun clearAllData() {
        withContext(dispatcherProvider.io()) {
            weatherDb.clearAllTables()

            File(picturesDir).deleteRecursively()
        }
    }

    override suspend fun clearWeatherOlderThan(date: Date) {
        weatherDb.weatherDao().dbDeleteWeatherOlderThan(date.time)
    }

    private suspend fun insertToDbWithResult(block: suspend WeatherDao.() -> Unit): WeatherOfflineSaveResultCode {
        return try {
            weatherDb.weatherDao().block()
            WeatherOfflineSaveResultCode.OK
        } catch (exception: Exception) {
            WeatherOfflineSaveResultCode.ERROR
        }
    }

    private suspend fun savePictureToFile(cityName: String, pictureBytes: ByteArray): File {
        return withContext(dispatcherProvider.io()) {
            val pictureFile = File(picturesDir).resolve(cityName)
            pictureFile.writeBytes(pictureBytes)
            pictureFile
        }
    }

    @Database(entities = [DbCityWeather::class, DbCityInfo::class], version = 1)
    abstract class WeatherDatabase : RoomDatabase() {
        abstract fun weatherDao(): WeatherDao
    }

    @Dao
    interface WeatherDao {

        @Query("""SELECT * 
            FROM t_city_info
            ORDER BY f_city_name""")
        suspend fun dbRequestAllCitiesInfo(): List<DbCityInfo>

        @Query("""SELECT * 
            FROM t_city_weather 
            WHERE f_city_name = :cityName
            ORDER BY f_time""")
        suspend fun dbRequestCityWeather(cityName: String): List<DbCityWeather>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun dbInsertCitiesInfos(citiesInfo: List<DbCityInfo>)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun dbInsertCitiesWeather(citiesWeather: List<DbCityWeather>)

        @Query("DELETE FROM t_city_weather WHERE f_time < :time")
        suspend fun dbDeleteWeatherOlderThan(time: Long)

        @Query("DELETE FROM t_city_info")
        suspend fun dbClearCitiesInfo()

        @Query("DELETE FROM t_city_weather")
        suspend fun dbClearCitiesWeather()

    }

    @Entity(tableName = "t_city_info")
    data class DbCityInfo(
        @PrimaryKey
        @ColumnInfo(name = "f_city_name")
        val cityName: String,

        @ColumnInfo(name = "f_picture_file_path")
        val pictureFilePath: String?
    )

    @Entity(tableName = "t_city_weather", primaryKeys = ["f_city_name", "f_time"])
    data class DbCityWeather(
        @ColumnInfo(name = "f_city_name")
        val cityName: String,

        @ColumnInfo(name = "f_time")
        val time: Long,

        @ColumnInfo(name = "f_temperature")
        val temperature: Float
    )

    companion object {
        private const val TAG = "WeatherOfflineRepo"
    }
}