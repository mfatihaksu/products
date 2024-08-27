package com.mfa.products

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.mfa.products.ui.theme.ProductsNavigation
import com.mfa.products.ui.theme.ProductsTheme

fun ComposeContentTestRule.launchProductsApp(context: Context) {
    setContent {
        ProductsTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ProductsNavigation(modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding())
            }
        }
    }
}