package com.app.humaraapnabazaar.di

import android.content.Context
import com.app.humaraapnabazaar.HumaraApnaBazaarApplication
import com.app.humaraapnabazaar.data.api.ApiService
import com.app.humaraapnabazaar.data.api.AuthInterceptor
import com.app.humaraapnabazaar.data.api.AuthenticatedApiService
import com.app.humaraapnabazaar.data.datasource.CategoryDataSource
import com.app.humaraapnabazaar.data.datasource.ProductDataSource
import com.app.humaraapnabazaar.data.datasource.UserDataSource
import com.app.humaraapnabazaar.data.implementation.CategoryRepositoryImplementation
import com.app.humaraapnabazaar.data.implementation.ProductRepositoryImplementation
import com.app.humaraapnabazaar.data.implementation.UserRepositoryImplementation
import com.app.humaraapnabazaar.data.pagination.ProductByCategoryPagingSource
import com.app.humaraapnabazaar.data.pagination.ProductsPagingSource
import com.app.humaraapnabazaar.data.repository.CategoryRepository
import com.app.humaraapnabazaar.data.repository.ProductRepository
import com.app.humaraapnabazaar.data.repository.UserRepository
import com.app.humaraapnabazaar.utils.TokenManager
import com.app.humaraapnabazaar.utils.TokenRefreshManager
import dagger.BindsInstance
import dagger.Component
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
  }

  @Singleton
  @Provides
  fun provideAuthInterceptor(
    tokenManager: TokenManager,
    userDataSource: Lazy<UserDataSource>,
  ): AuthInterceptor {
    return AuthInterceptor(tokenManager, userDataSource)
  }

  @Singleton
  @Provides
  fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
    return TokenManager(context)
  }

  @Singleton
  @Provides
  fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(authInterceptor)
      .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
      .readTimeout(30, TimeUnit.SECONDS) // Set read timeout
      .writeTimeout(30, TimeUnit.SECONDS) // Set write timeout
      .build()
  }

  @Provides
  @Singleton
  fun provideTokenRefreshManager(
    tokenManager: TokenManager,
    userDataSource: Lazy<UserDataSource>,
  ): TokenRefreshManager {
    return TokenRefreshManager(tokenManager, userDataSource)
  }

  @Singleton
  @Provides
  fun provideRetrofitBuilder(): Retrofit.Builder {
    return Retrofit.Builder()
      .baseUrl("https://www.humarapnabazaar.com/api/v1/")
      .addConverterFactory(GsonConverterFactory.create())
  }

  @Singleton
  @Provides
  fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
    return retrofitBuilder.build().create(ApiService::class.java)
  }

  @Singleton
  @Provides
  fun provideAuthenticatedApiService(
    retrofitBuilder: Retrofit.Builder,
    okHttpClient: OkHttpClient,
  ): AuthenticatedApiService {
    return retrofitBuilder.client(okHttpClient).build().create(AuthenticatedApiService::class.java)
  }

  @Singleton
  @Provides
  fun provideUserRepository(
    userRepositoryImplementation: UserRepositoryImplementation
  ): UserRepository {
    return userRepositoryImplementation
  }

  @Singleton
  @Provides
  fun provideUserDataSource(apiService: ApiService, authenticatedApiService: AuthenticatedApiService): UserDataSource {
    return UserDataSource(apiService, authenticatedApiService)
  }

  @Singleton
  @Provides
  fun provideCategoryRepository(
    categoryRepositoryImplementation: CategoryRepositoryImplementation
  ): CategoryRepository {
    return categoryRepositoryImplementation
  }

  @Singleton
  @Provides
  fun provideCategoryDataSource(
    authenticatedApiService: AuthenticatedApiService
  ): CategoryDataSource {
    return CategoryDataSource(authenticatedApiService)
  }

  @Singleton
  @Provides
  fun provideProductDataSource(
    authenticatedApiService: AuthenticatedApiService
  ): ProductDataSource {
    return ProductDataSource(authenticatedApiService)
  }

  @Singleton
  @Provides
  fun provideProductRepository(
    productRepositoryImplementation: ProductRepositoryImplementation
  ): ProductRepository {
    return productRepositoryImplementation
  }

  @Singleton
  @Provides
  fun provideDataStore(
    @ApplicationContext context: Context
  ): androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> {
    return androidx.datastore.preferences
      .preferencesDataStore(name = "user_preferences")
      .getValue(context, String::javaClass)
  }

  @Singleton
  @Provides
  fun provideProductsPagingSource(productRepository: ProductRepository): ProductsPagingSource {
    return ProductsPagingSource(productRepository)
  }

  @Singleton
  @Provides
  fun provideProductByCategoryPagingSource(productRepository: ProductRepository, category: String): ProductByCategoryPagingSource {
    return ProductByCategoryPagingSource(productRepository, category)
  }
  @Singleton
  @Provides
  fun provideContext(@ApplicationContext context: Context): Context {
    return context
  }

}


