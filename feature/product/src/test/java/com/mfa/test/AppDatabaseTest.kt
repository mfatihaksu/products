package com.mfa.test


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.mfa.data.data.AppDatabase
import com.mfa.data.data.Product
import com.mfa.data.data.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppDatabaseTest {

    private lateinit var database: AppDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        productDao = database.productDao()
    }

    @Test
    fun insert_product_returns_true() = runBlocking {
        val product = Product(uId = Random.nextInt(), id = "1", name = "test", price = 1, image = "test.png", description = "test")
        productDao.insert(product)
        val latch = CountDownLatch(1)
        val deferred = async(Dispatchers.IO) {
            val products = productDao.getProducts()
            assert(products.contains(product))
            latch.countDown()
        }
        latch.await()
        deferred.cancelAndJoin()
    }

    @After
    fun closeDatabase(){
        database.close()
    }
}