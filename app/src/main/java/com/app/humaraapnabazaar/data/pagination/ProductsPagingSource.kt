package com.app.humaraapnabazaar.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.humaraapnabazaar.data.model.Product
import com.app.humaraapnabazaar.data.repository.ProductRepository
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(private val productRepository: ProductRepository) :
  PagingSource<Int, Product>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
    return try {
      val currentPage = params.key ?: 1
      val pageSize = params.loadSize
      val response = productRepository.getProducts(currentPage, pageSize)

      LoadResult.Page(
        data = response.body()?.products ?: emptyList(),
        prevKey = if (currentPage == 1) null else currentPage - 1,
        nextKey = if (response.body()?.products?.size!! < pageSize) null else currentPage + 1,
      )
    } catch (exception: Exception) {
      LoadResult.Error(exception)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}
