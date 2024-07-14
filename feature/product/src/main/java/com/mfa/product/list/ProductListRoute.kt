package com.mfa.product.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mfa.core.ui.Loading
import com.mfa.data.data.Product
import com.mfa.ui.AppAlertDialog

@Composable
fun ProductListRoute(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = hiltViewModel(),
    onClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    ProductListScreen(uiState = uiState, modifier = modifier) {
        onClick(it)
    }
}

@Composable
internal fun ProductListScreen(
    uiState: ProductListUIState,
    modifier: Modifier,
    onClick: (String) -> Unit
) {
    when (uiState) {
        is ProductListUIState.Loading -> Loading()
        is ProductListUIState.Success -> ProductListContent(
            modifier = modifier,
            products = uiState.products,
            onClick = onClick
        )

        is ProductListUIState.Failure ->
            AppAlertDialog(
                dialogTitle = "Error",
                dialogText = uiState.message,
                onDismissRequest = {},
                onConfirmation = {})
    }
}

@Composable
internal fun ProductListContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onClick: (String) -> Unit
) {
    val gridState = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalItemSpacing = 10.dp,
        content = {
            itemsIndexed(products) { _, item ->
                ProductItemView(modifier = Modifier, product = item, onClick = onClick)
            }
        }
    )
}

@Composable
internal fun ProductItemView(modifier: Modifier, product: Product, onClick: (String) -> Unit) {
    Column(modifier = modifier.clickable {
        onClick(product.id.orEmpty())
    }, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = product.image, contentDescription = product.name)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = product.name.orEmpty(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = product.price.toString(), textAlign = TextAlign.Center)
    }
}