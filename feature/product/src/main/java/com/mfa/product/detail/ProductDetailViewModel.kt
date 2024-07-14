package com.mfa.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfa.data.data.Product
import com.mfa.data.data.ProductRepository
import com.mfa.data.helper.OperationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
        productRepository.getProduct(id = productId)
            .collectLatest { operationResult: OperationResult<Product> ->
                when (operationResult) {
                    is OperationResult.Loading -> {
                        send(ProductDetailUIState.Loading)
                    }

                    is OperationResult.Success -> {
                        send(ProductDetailUIState.Success(operationResult.data))
                    }

                    is OperationResult.Failure -> {
                        send(ProductDetailUIState.Failure(operationResult.errorMessage.orEmpty()))
                    }
                }
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductDetailUIState.Loading)
}