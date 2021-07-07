package uz.abuyork.k_phone.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import uz.abuyork.k_phone.R
import uz.abuyork.k_phone.screen.MainViewModel
import uz.abuyork.k_phone.utils.PrefUtills
import uz.abuyork.k_phone.view.ProductAdapter

class FavoriteFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.productsData.observe(this, Observer {
            recyclerProducts.adapter = ProductAdapter(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            swipe.isRefreshing = it
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerProducts.layoutManager = LinearLayoutManager(requireActivity())

        swipe.setOnRefreshListener {
            loadData()
        }
        loadData()
    }

    fun loadData(){
        viewModel.getProductsByIds(PrefUtills.getFavoriteList())
    }

    companion object {

        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}