package uz.abuyork.k_phone.screen

import android.provider.ContactsContract
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.abuyork.k_phone.api.Api
import uz.abuyork.k_phone.api.NetworkManager
import uz.abuyork.k_phone.api.repository.ShopRepository
import uz.abuyork.k_phone.db.AppDatabese
import uz.abuyork.k_phone.model.BaseResponse
import uz.abuyork.k_phone.model.CategoryModel
import uz.abuyork.k_phone.model.OfferModel
import uz.abuyork.k_phone.model.ProductModel
import uz.abuyork.k_phone.utils.Constants
import uz.abuyork.k_phone.view.CategoryAdapter

class MainViewModel: ViewModel() {
    val repository = ShopRepository()

    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()

    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>>()

    fun getOffers(){
        repository.getOffers(error, progress, offersData)
    }

    fun getCategories(){
        repository.getCategories(error, categoriesData)
    }

    fun getTopProducts(){
        repository.getTopProducts(error,productsData)
    }

    fun getProductsByCategory(id: Int){
        repository.getProductsByCategory(id, error, productsData)
    }

    fun getProductsByIds(ids: List<Int>){
        repository.getProductsByIds(ids, error, progress, productsData)
    }

    fun insertAllProducts2DB(items: List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabese.getDatabase().getProductDao().deleteAll()
            AppDatabese.getDatabase().getProductDao().insertAll(items)
        }
    }

    fun insertAllCategories2DB(items: List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabese.getDatabase().getCategoryDao().deleteAll()
            AppDatabese.getDatabase().getCategoryDao().insetAll(items)
        }
    }

    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch{
            productsData.value = withContext(Dispatchers.IO){AppDatabese.getDatabase().getProductDao().getAllProducts()}
        }
    }

    fun getAllDBCategories(){
        CoroutineScope(Dispatchers.Main).launch{
            categoriesData.value = withContext(Dispatchers.IO){AppDatabese.getDatabase().getCategoryDao().getAllCategories()}
        }
    }
}