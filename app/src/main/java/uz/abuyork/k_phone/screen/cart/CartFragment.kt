package uz.abuyork.k_phone.screen.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cart.*
import uz.abuyork.k_phone.R
import uz.abuyork.k_phone.model.ProductModel
import uz.abuyork.k_phone.screen.MainViewModel
import uz.abuyork.k_phone.screen.makeorder.MakeOrderActivity
import uz.abuyork.k_phone.screen.sign.LoginActivity
import uz.abuyork.k_phone.utils.Constants
import uz.abuyork.k_phone.utils.PrefUtills
import uz.abuyork.k_phone.view.CartAdapter
import java.io.Serializable

class CartFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(this, Observer {
            swipe.isRefreshing = it
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.productsData.observe(this, Observer {
            recycler.adapter = CartAdapter(it)
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireActivity())

        swipe.setOnRefreshListener {
            loadData()
        }

        btnMakeOrder.setOnClickListener {
            if (PrefUtills.getToken().isNullOrEmpty()){
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }else{
                val intent = Intent(requireActivity(), MakeOrderActivity::class.java)
                intent.putExtra(Constants.EXTRA_DATA, (viewModel.productsData.value ?: emptyList<ProductModel>()) as Serializable)
                startActivity(intent)
            }
        }

        loadData()
    }

    fun loadData(){
        viewModel.getProductsByIds(PrefUtills.getCartList().map { it.id })
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}