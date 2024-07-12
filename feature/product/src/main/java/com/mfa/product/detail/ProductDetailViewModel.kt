package com.mfa.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfa.data.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    productRepository: ProductRepository
) : ViewModel() {

    private val productId: String = checkNotNull(savedStateHandle["id"])

    val uiState: StateFlow<ProductDetailUIState> = channelFlow {
        val detailUIState = ProductDetailUIState()
        productRepository.getProduct(productId).onSuccess {
            launch(Dispatchers.IO) {
                detailUIState.isLoading = false
                detailUIState.product = it
                send(detailUIState)
            }
        }
            .onFailure { exception ->
                launch(Dispatchers.IO) {
                    detailUIState.isLoading = false
                    detailUIState.errorMessage = exception.message
                    trySend(detailUIState)
                }
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductDetailUIState())
}