package com.example.weatherforecast.data

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
import com.example.city_list.domain.WeatherOfflineRepository
import com.example.city_list.models.data.WeatherOfflineSaveResultCode
import com.example.common.logs.log
import com.example.common.models.CityInfo
import com.example.common.models.CityWeather
import com.example.weather_details.domain.WeatherDetailsRepository
import com.example.weatherforecast.common.ApplicationProvider
import javax.inject.Inject

class WeatherOfflineRepositoryImpl
    @Inject
    constructor(
        private val applicationProvider: ApplicationProvider
    ) : WeatherOfflineRepository, WeatherDetailsRepository {

    private val weatherDb: WeatherDatabase by lazy {
        Room.databaseBuilder(
            applicationProvider.applicationContext,
            WeatherDatabase::class.java, "weather_database.db"
        ).build()
    }

    override suspend fun requestAllCitiesInfo(): Map<String, CityInfo> {
        return weatherDb.weatherDao().dbRequestAllCitiesInfo().associateBy({ dbCityInfo ->
            dbCityInfo.cityName
        }, { dbCityInfo ->
            CityInfo(dbCityInfo.pictureUrl.takeIf { !it.isNullOrBlank() })
        })
    }

    override suspend fun requestCityWeather(cityName: String): Map<Long, Float> {
        return weatherDb.weatherDao().dbRequestCityWeather(cityName).associateBy({ dbCityWeather ->
            dbCityWeather.time
        }, { dbCityWeather ->
            dbCityWeather.temperature
        })
    }

    override suspend fun requestCityInfo(cityName: String): CityInfo {
        return weatherDb.weatherDao().dbRequestCityInfo(cityName).let { CityInfo(it.pictureUrl) }
    }

    override suspend fun saveCitiesInfo(citiesInfo: Map<String, CityInfo>): WeatherOfflineSaveResultCode {
        val dbCitiesInfo: MutableList<DbCityInfo> = mutableListOf()

        citiesInfo.forEach { entry ->
            val cityName = entry.key
            val cityInfo = entry.value

            val dbCityInfo = DbCityInfo(cityName, cityInfo.pictureUrl)
            dbCitiesInfo.add(dbCityInfo)


            log { i(TAG, "WeatherOfflineRepositoryImpl.saveCitiesInfo(). $dbCityInfo") }
        }

        return insertToDbWithResult { dbInsertCitiesInfos(dbCitiesInfo) }
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
        weatherDb.weatherDao().dbClearCitiesInfo()
    }

    override suspend fun clearCitiesWeather() {
        weatherDb.weatherDao().dbClearCitiesWeather()
    }

    override suspend fun clearAllData() {
        weatherDb.clearAllTables()
    }

    override suspend fun clearWeatherOlderThan(time: Long) {
        weatherDb.weatherDao().dbDeleteWeatherOlderThan(time)
    }

    private suspend fun insertToDbWithResult(block: suspend WeatherDao.() -> Unit): WeatherOfflineSaveResultCode {
        return try {
            weatherDb.weatherDao().block()
            WeatherOfflineSaveResultCode.OK
        } catch (exception: Exception) {
            WeatherOfflineSaveResultCode.ERROR
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
            FROM t_city_info
            WHERE f_city_name = :cityName""")
        suspend fun dbRequestCityInfo(cityName: String): DbCityInfo

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

        @ColumnInfo(name = "f_picture_url")
        val pictureUrl: String?
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