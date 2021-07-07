package uz.abuyork.k_phone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.abuyork.k_phone.model.ProductModel

@Dao
interface ProductDao {

    @Query("DELETE from products")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ProductModel>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductModel>
}