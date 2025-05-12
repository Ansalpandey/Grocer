package com.app.grocer.data.implementation

import com.app.grocer.common.Resource
import com.app.grocer.data.datasource.CategoryDataSource
import com.app.grocer.data.model.CategoriesResponseItem
import com.app.grocer.data.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImplementation
@Inject
constructor(private val categoryDataSource: CategoryDataSource) : CategoryRepository {
  override suspend fun getCategories(): Flow<Resource<List<CategoriesResponseItem>>> {
    return categoryDataSource.getCategories()
  }
}
