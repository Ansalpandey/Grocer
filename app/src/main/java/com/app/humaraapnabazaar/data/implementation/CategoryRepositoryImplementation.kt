package com.app.humaraapnabazaar.data.implementation

import com.app.humaraapnabazaar.common.Resource
import com.app.humaraapnabazaar.data.datasource.CategoryDataSource
import com.app.humaraapnabazaar.data.model.CategoriesResponseItem
import com.app.humaraapnabazaar.data.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImplementation
@Inject
constructor(private val categoryDataSource: CategoryDataSource) : CategoryRepository {
  override suspend fun getCategories(): Flow<Resource<List<CategoriesResponseItem>>> {
    return categoryDataSource.getCategories()
  }
}
