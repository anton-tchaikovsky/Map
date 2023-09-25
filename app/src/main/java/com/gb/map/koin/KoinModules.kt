package com.gb.map.koin

import androidx.room.Room
import com.gb.map.data.GeocoderProvider
import com.gb.map.data.GeocoderProviderImpl
import com.gb.map.data.LocationProvider
import com.gb.map.data.LocationProviderImpl
import com.gb.map.data.data_source.LocalDataSource
import com.gb.map.data.data_source.LocalDataSourceImpl
import com.gb.map.data.mapper.LocationMapper
import com.gb.map.data.mapper.LocationMapperImpl
import com.gb.map.data.room.MapDatabase
import com.gb.map.presentation.MapContract
import com.gb.map.presentation.MapFragment
import com.gb.map.presentation.MapPresenterImpl
import com.gb.map.repository.LocationRepository
import com.gb.map.repository.LocationRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module {
    single<GeocoderProvider> { GeocoderProviderImpl(context = androidApplication()) }

    single<LocationProvider> { LocationProviderImpl(context = androidApplication()) }

    single<LocationRepository> {
        LocationRepositoryImpl(
            locationProvider = get(),
            geocoderProvider = get(),
            locationMapper = get(),
            localDataSource = get()
        )
    }

    single<LocationMapper> { LocationMapperImpl() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            MapDatabase::class.java,
            "map_database"
        )
            .build()
    }

    single <LocalDataSource> { LocalDataSourceImpl(mapDatabase = get())}

}

val activityModule = module {
    scope(named<MapFragment>()) {
        scoped<MapContract.MapPresenter> { MapPresenterImpl(locationRepository = get()) }
    }
}