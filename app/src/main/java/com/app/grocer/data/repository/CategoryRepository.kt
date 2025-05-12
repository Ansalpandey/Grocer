package com.app.grocer.data.repository

import com.app.grocer.common.Resource
import com.app.grocer.data.model.CategoriesResponseItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
  suspend fun getCategories(): Flow<Resource<List<CategoriesResponseItem>>>
}
