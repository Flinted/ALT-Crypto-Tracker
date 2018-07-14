package this.chrisdid.alt.injection.modules

import dagger.Module
import dagger.Provides
import this.chrisdid.alt.BuildConfig
import this.chrisdid.alt.data.dataController.DataController
import this.chrisdid.alt.data.dataController.cache.UIObjectCache
import this.chrisdid.alt.data.dataController.dataManagers.ApiRepository
import this.chrisdid.alt.data.dataController.dataManagers.RealmManager
import this.chrisdid.alt.data.services.interfaces.CMCAPIService
import this.chrisdid.alt.data.services.interfaces.CryptoCompareAPIService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * DataModule
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
@Module
open class DataModule() {

    private fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Content-Type:", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
            chain.proceed(request)
        }
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOKHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .addNetworkInterceptor(provideHeaderInterceptor())
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Named("CoinMarketCap")
    fun provideCMCRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val callAdapterFactory = RxJavaCallAdapterFactory.create()
        val gsonConverterFactory = GsonConverterFactory.create()
        return Retrofit.Builder()
                .baseUrl("https://api.coinmarketcap.com")
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Named("CryptoCompare")
    fun provideCrypoCompareRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val callAdapterFactory = RxJavaCallAdapterFactory.create()
        val gsonConverterFactory = GsonConverterFactory.create()
        return Retrofit.Builder()
                .baseUrl("https://min-api.cryptocompare.com")
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build()
    }

    // Services

    @Provides
    @Singleton
    fun provideCMCAPIService(@Named("CoinMarketCap") retrofit: Retrofit): CMCAPIService {
        return retrofit.create(CMCAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoCompareAPIService(@Named("CryptoCompare") retrofit: Retrofit): CryptoCompareAPIService {
        return retrofit.create(CryptoCompareAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiManager(cmcApiService: CMCAPIService,
                          cryptoCompareAPIService: CryptoCompareAPIService):
            ApiRepository {
        return ApiRepository(cmcApiService, cryptoCompareAPIService)
    }

    @Provides
    @Singleton
    fun provideRealmManager(): RealmManager {
        return RealmManager()
    }

    @Provides
    @Singleton
    fun provideDataController(apiManager: ApiRepository, realmManager: RealmManager, uiObjectCache: UIObjectCache): DataController {
        return DataController(apiManager, realmManager, uiObjectCache)
    }
}
