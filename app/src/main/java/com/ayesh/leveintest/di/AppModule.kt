package com.ayesh.leveintest.di

import com.ayesh.leveintest.BuildConfig
import com.ayesh.leveintest.data.remote.APIClass
import com.ayesh.leveintest.data.repository.AuthorRepositoryImpl
import com.ayesh.leveintest.domain.repository.AuthorRepository
import com.ayesh.leveintest.domain.usecase.author.GetAuthorsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun authAPI(): APIClass {
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(APIClass::class.java)
    }

    @Provides
    @Singleton
    fun getAuthorRepository(apiClass: APIClass): AuthorRepository {
        return AuthorRepositoryImpl(apiClass)
    }

    @Provides
    @Singleton
    fun provideGetAuthorUseCase(repository: AuthorRepository): GetAuthorsUseCase =
        GetAuthorsUseCase(repository)
}