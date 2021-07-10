package uz.abuyork.k_phone.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.abuyork.k_phone.api.repository.ShopRepository
import uz.abuyork.k_phone.db.AppDatabese
import uz.abuyork.k_phone.model.CategoryModel
import uz.abuyork.k_phone.model.OfferModel
import uz.abuyork.k_phone.model.ProductModel
import uz.abuyork.k_phone.model.*

class MainViewModel: ViewModel() {
    val repository = ShopRepository()

    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()

    val checkPhoneData = MutableLiveData<CheckPhoneResponse>()
    val registrationData = MutableLiveData<Boolean>()
    val confirmData = MutableLiveData<LoginResponse>()
    val loginData = MutableLiveData<LoginResponse>()
    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>>()
    val makeOrderData = MutableLiveData<Boolean>()

    fun checkPhone(phone: String){
        repository.checkPhone(phone, error, progress, checkPhoneData)
    }

    fun registrationData(fullname: String, phone: String, password: String){
        repository.registration(fullname, phone, password, error, progress, registrationData)
    }

    fun login(phone: String, password: String){
        repository.login(phone, password, error, progress, loginData)
    }

    fun confirmUser(phone: String, code: String){
        repository.confirmUser(phone, code, error, progress, confirmData)
    }

    fun getOffers(){
        repository.getOffers(error, progress, offersData)
    }

    fun getCategories(){
        repository.getCategories(error, categoriesData)
    }

    fun getTopProducts(){
        repository.getTopProducts(error, productsData)
    }

    fun getProductsByCategory(id: Int){
        repository.getProductsByCategory(id, error, productsData)
    }

    fun getProductsByIds(ids: List<Int>){
        repository.getProductsByIds(ids, error, progress, productsData)
    }

    fun makeOrder(products: List<CartModel>, lat: Double, lon: Double, comment: String){
        repository.makeOrder(products, lat, lon, comment, error, progress, makeOrderData)
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