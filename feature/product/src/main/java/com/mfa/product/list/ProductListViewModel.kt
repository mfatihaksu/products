package com.mfa.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfa.data.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    productRepository: ProductRepository
) : ViewModel() {
    val uiState: StateFlow<ProductListUIState> = channelFlow {
        productRepository.getProducts().collectLatest {
            send(ProductListUIState(isLoading = false, products = it.toList()))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListUIState())
}