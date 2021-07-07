package uz.abuyork.k_phone.screen.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_product_detail.tvTitle
import kotlinx.android.synthetic.main.category_item_layout.*
import uz.abuyork.k_phone.R
import uz.abuyork.k_phone.model.ProductModel
import uz.abuyork.k_phone.utils.Constants
import uz.abuyork.k_phone.utils.PrefUtills

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        cardViewBack.setOnClickListener {
            finish()
        }

        cardViewFavorite.setOnClickListener {
            PrefUtills.setFavorite(item)

            if (PrefUtills.checkFavorite(item)){
                imgFavorite.setImageResource(R.drawable.ic_love__1_)
            }else {
                imgFavorite.setImageResource(R.drawable.ic_love)
            }

        }
        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        tvTitle.text = item.name
        tvProductName.text = item.name
        tvProductPrice.text = item.price

        if (PrefUtills.getCartCount(item)> 0){
            btnAdd2Cart.visibility = View.GONE
        }

        if (PrefUtills.checkFavorite(item)){
            imgFavorite.setImageResource(R.drawable.ic_love__1_)
        }else {
            imgFavorite.setImageResource(R.drawable.ic_love)
        }

        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(imgProduct)


        btnAdd2Cart.setOnClickListener {
            item.cartCount = 1
            PrefUtills.setCart(item)
            Toast.makeText(this,"Product added to cart!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}