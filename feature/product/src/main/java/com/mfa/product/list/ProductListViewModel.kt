package com.mfa.product.list

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
class ProductListViewModel @Inject constructor(
    productRepository: ProductRepository
) : ViewModel() {
    val uiState: StateFlow<ProductListUIState> = channelFlow {
        productRepository.getProducts()
            .collectLatest { operationResult: OperationResult<List<Product>> ->
                when (operationResult) {
                    is OperationResult.Loading -> {
                        send(ProductListUIState.Loading)
                    }
                    is OperationResult.Success -> {

                        send(ProductListUIState.Success(operationResult.data.orEmpty()))
                    }
                    is OperationResult.Failure -> {
                        send(ProductListUIState.Failure(operationResult.errorMessage.orEmpty()))
                    }
                }
            }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListUIState.Loading)
}