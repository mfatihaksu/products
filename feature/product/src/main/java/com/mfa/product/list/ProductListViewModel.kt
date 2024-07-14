package com.mfa.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfa.data.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    productRepository: ProductRepository
) : ViewModel() {
    val uiState: StateFlow<ProductListUIState> = channelFlow {
        val uiState = ProductListUIState()
        productRepository.getProducts().catch {
            uiState.isLoading = false
            uiState.errorMessage = it.message
            send(uiState)
        }.collectLatest {
            uiState.isLoading = false
            uiState.products = it.toList()
            send(uiState)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListUIState())
}