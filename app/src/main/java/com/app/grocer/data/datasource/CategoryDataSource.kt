package com.app.grocer.data.datasource

import com.app.grocer.common.Resource
import com.app.grocer.data.api.AuthenticatedApiService
import com.app.grocer.data.model.CategoriesResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryDataSource
@Inject
constructor(private val authenticatedApiService: AuthenticatedApiService) {
  fun getCategories(): Flow<Resource<List<CategoriesResponseItem>>> = flow {
    emit(Resource.Loading())
    try {
      val response = authenticatedApiService.getCategories()
      if (response.isSuccessful && response.body() != null) {
        emit(Resource.Success(response.body()!!))
      } else {
        emit(Resource.Error("Failed to fetch categories"))
      }
    } catch (e: Exception) {
      emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
    }
  }
}
