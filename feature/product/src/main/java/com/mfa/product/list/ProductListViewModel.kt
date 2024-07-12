package com.mfa.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfa.data.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    productRepository: ProductRepository
) : ViewModel() {

    val uiState: StateFlow<ProductListUIState> = channelFlow {
        productRepository.getProducts()
            .onSuccess {
                launch(Dispatchers.IO) {
                    send(ProductListUIState(isLoading = false, products = it.products))
                }
            }
            .onFailure { exception->
               launch(Dispatchers.IO) {
                    trySend(ProductListUIState(isLoading = false, products = null, errorMessage = exception.message))
                }
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductListUIState())
}