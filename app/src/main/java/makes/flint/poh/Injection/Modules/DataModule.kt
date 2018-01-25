package makes.flint.poh.injection.modules

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import makes.flint.poh.BuildConfig
import makes.flint.poh.data.dataController.DataController
import makes.flint.poh.data.dataController.dataManagers.ApiManager
import makes.flint.poh.data.dataController.dataManagers.RealmManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * DataModule
 * Copyright © 2018 Flint Makes.. All rights reserved.
 */
@Module open class DataModule() {

    @Provides
    @Singleton
    fun provideDataController(apiManager: ApiManager, realmManager: RealmManager): DataController {
        return DataController(apiManager, realmManager)
    }

    @Provides
    @Singleton
    fun provideApiManager(): ApiManager {
        return ApiManager()
    }

    @Provides
    @Singleton
    fun provideRealmManager(realm: Realm): RealmManager {
        return RealmManager(realm)
    }

    @Provides
    internal fun provideRealm(): Realm {
        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        return Realm.getDefaultInstance()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor();
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor;
    }

    @Provides
    fun provideHeaderInterceptor(): Interceptor {
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
    fun provideOKHttpClinet(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient? {
        val builder = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG ) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        val client =  builder.build()
        return client
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit? {
        val baseURL = ""
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
}
