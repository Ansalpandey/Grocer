package com.app.humaraapnabazaar.data.repository

import com.app.humaraapnabazaar.common.Resource
import com.app.humaraapnabazaar.data.model.CategoriesResponseItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
  suspend fun getCategories(): Flow<Resource<List<CategoriesResponseItem>>>
}
