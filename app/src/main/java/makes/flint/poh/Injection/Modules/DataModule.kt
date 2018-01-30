package makes.flint.poh.injection.modules

import dagger.Module
import dagger.Provides
import makes.flint.poh.BuildConfig
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.dataManagers.ApiRepository
import makes.flint.poh.data.dataController.dataManagers.RealmManager
import makes.flint.poh.data.services.interfaces.CMCAPIService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * DataModule
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
@Module open class DataModule() {

    private fun provideHeaderInterceptor(): Interceptor {
        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Content-Type:", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
            chain.proceed(request)
        }
        return interceptor
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor();
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOKHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .addNetworkInterceptor(provideHeaderInterceptor())
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG ) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        val client =  builder.build()
        return client
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val baseURL = "https://api.coinmarketcap.com"
        val callAdapterFactory = RxJavaCallAdapterFactory.create()
        val gsonConverterFactory = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build()
        return retrofit
    }

    // Services

    @Provides
    fun provideCryptoCompareAPIService(retrofit: Retrofit): CMCAPIService {
        return retrofit.create(CMCAPIService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiManager(cryptoCompareAPIService: CMCAPIService): ApiRepository {
        return ApiRepository(cryptoCompareAPIService)
    }

    @Provides
    @Singleton
    fun provideRealmManager(): RealmManager {
        return RealmManager()
    }

    @Provides
    @Singleton
    fun provideDataController(apiManager: ApiRepository, realmManager: RealmManager): DataController {
        return DataController(apiManager, realmManager)
    }

}
