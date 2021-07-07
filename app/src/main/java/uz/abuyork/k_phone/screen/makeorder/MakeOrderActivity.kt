package uz.abuyork.k_phone.screen.makeorder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_make_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.abuyork.k_phone.MapsActivity
import uz.abuyork.k_phone.R
import uz.abuyork.k_phone.model.AdressModel
import uz.abuyork.k_phone.model.ProductModel
import uz.abuyork.k_phone.utils.Constants

class MakeOrderActivity : AppCompatActivity() {
    var address: AdressModel? = null
    lateinit var items: List<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)

        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>

        if (!EventBus.getDefault().isRegistered(this)){
           EventBus.getDefault().register(this)
        }

        tvTotalAmount.setText(items.sumOf { it.cartCount.toDouble() * (it.price.replace(" ","").toDoubleOrNull() ?: 0.0) }.toString())
        edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(address: AdressModel){
        this.address = address
        edAddress.setText("${address.latitude}, ${address.longtitude}")
    }
}
