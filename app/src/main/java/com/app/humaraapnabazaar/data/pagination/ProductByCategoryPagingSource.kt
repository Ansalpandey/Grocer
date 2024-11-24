package com.app.humaraapnabazaar.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.humaraapnabazaar.data.model.Product
import com.app.humaraapnabazaar.data.repository.ProductRepository
import javax.inject.Inject

class ProductByCategoryPagingSource
@Inject
constructor(private val productRepository: ProductRepository, private val category: String) :
  PagingSource<Int, Product>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
    val page = params.key ?: 1
    try {
      val response = productRepository.getProductsByCategory(category, page, params.loadSize)
      if (response.isSuccessful) {
        val products = response.body()?.products ?: emptyList()
        return LoadResult.Page(
          data = products,
          prevKey = if (page == 1) null else page - 1,
          nextKey = if (products.isEmpty()) null else page + 1
        )
      } else {
        return LoadResult.Error(Exception("API call failed"))
      }
    } catch (exception: Exception) {
      return LoadResult.Error(exception)
    }
  }


  override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}
