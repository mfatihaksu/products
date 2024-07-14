package com.mfa.product.detail

import androidx.lifecycle.SavedStateHandle
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
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    productRepository: ProductRepository
) : ViewModel() {

    private val productId: String = checkNotNull(savedStateHandle["id"])

    val uiState: StateFlow<ProductDetailUIState> = channelFlow {
        val uiState = ProductDetailUIState()
        productRepository.getProduct(productId)
            .catch {
                uiState.isLoading = false
                uiState.errorMessage = it.message
                send(uiState)
            }
            .collectLatest {
                uiState.isLoading = false
                uiState.product = it
                send(uiState)
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductDetailUIState())
}