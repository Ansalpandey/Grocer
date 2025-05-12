package com.app.grocer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.grocer.common.Resource
import com.app.grocer.data.model.CategoriesResponseItem
import com.app.grocer.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
  ViewModel() {
  private val _categories =
    MutableStateFlow<Resource<List<CategoriesResponseItem>>>(Resource.Loading())
  val categories: StateFlow<Resource<List<CategoriesResponseItem>>> = _categories.asStateFlow()

  init {
    getCategories()
  }

  private fun getCategories() {
    viewModelScope.launch {
      categoryRepository.getCategories().collect { result -> _categories.value = result }
    }
  }
}
