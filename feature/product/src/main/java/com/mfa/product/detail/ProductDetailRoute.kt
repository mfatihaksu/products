package com.mfa.product.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mfa.core.ui.Loading
import com.mfa.data.data.Product
import com.mfa.ui.AppAlertDialog

@Composable
fun ProductDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ProductDetailScreen(uiState, modifier)
}

@Composable
internal fun ProductDetailScreen(uiState: ProductDetailUIState, modifier: Modifier = Modifier) {
    if (uiState.isLoading) {
        Loading()
    } else {
        if (uiState.errorMessage.isNullOrEmpty().not()) {
            AppAlertDialog(
                dialogTitle = "Error",
                dialogText = uiState.errorMessage.orEmpty(),
                onDismissRequest = {},
                onConfirmation = {})
        } else {
            uiState.product?.let {
                ProductDetailContent(
                    modifier = modifier,
                    product = it
                )
            }
        }
    }
}

@Composable
internal fun ProductDetailContent(modifier: Modifier = Modifier, product: Product) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = product.image, contentDescription = product.name)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = product.name.orEmpty())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.price.toString())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.description.toString())
    }
}