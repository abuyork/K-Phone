package uz.abuyork.k_phone.api.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.abuyork.k_phone.api.NetworkManager
import uz.abuyork.k_phone.model.BaseResponse
import uz.abuyork.k_phone.model.CategoryModel
import uz.abuyork.k_phone.model.OfferModel
import uz.abuyork.k_phone.model.ProductModel
import uz.abuyork.k_phone.model.request.GetProductsByIdsRequest
import uz.abuyork.k_phone.utils.PrefUtills

class ShopRepository{
    val compositeDisposable = CompositeDisposable()

    fun getOffers(error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<OfferModel>>){
        progress.value = true
        compositeDisposable.add(NetworkManager.getApiService().getOffers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<BaseResponse<List<OfferModel>>>(){
                override fun onComplete() {

                }

                override fun onNext(t: BaseResponse<List<OfferModel>>) {
                   progress.value = false
                   if (t.success){
                       success.value = t.data
                   }else{
                       error.value = t.message
                   }
                }

                override fun onError(e: Throwable) {
                    progress.value = false
                    error.value = e.localizedMessage
                }
            })
        )
    }

    fun getCategories(error: MutableLiveData<String>, success: MutableLiveData<List<CategoryModel>>){
        compositeDisposable.add(NetworkManager.getApiService().getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<BaseResponse<List<CategoryModel>>>(){
                override fun onComplete() {

                }

                override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                    if (t.success){
                        success.value = t.data
                    }else{
                        error.value = t.message
                    }
                }

                override fun onError(e: Throwable) {
                    error.value = e.localizedMessage
                }
            })
        )
    }

    fun getTopProducts(error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>) {
        compositeDisposable.add(
            NetworkManager.getApiService().getTopProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun getProductsByCategory(id: Int, error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>) {
        compositeDisposable.add(
            NetworkManager.getApiService().getCategoryProducts(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun getProductsByIds(ids: List<Int>, error: MutableLiveData<String>,  progress: MutableLiveData<Boolean>, success: MutableLiveData<List<ProductModel>>) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getProductsByIds(GetProductsByIdsRequest(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        progress.value = false
                        if (t.success) {
                            t.data.forEach {
                                it.cartCount = PrefUtills.getCartCount(it)
                            }
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }
}