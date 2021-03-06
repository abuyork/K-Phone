package uz.abuyork.k_phone.screen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import uz.abuyork.k_phone.screen.changelanguage.ChangeLanguageFragment
import uz.abuyork.k_phone.screen.favorite.FavoriteFragment
import uz.abuyork.k_phone.screen.home.HomeFragment
import uz.abuyork.k_phone.screen.profile.ProfileFragment
import uz.abuyork.k_phone.utils.LocaleManager
import uz.abuyork.k_phone.R
import uz.abuyork.k_phone.screen.cart.CartFragment

class MainActivity : AppCompatActivity() {
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()

        viewModel.productsData.observe(this, Observer {
            viewModel.insertAllProducts2DB(it)
        })

        viewModel.categoriesData.observe(this, Observer {
            viewModel.insertAllCategories2DB(it)
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })


        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, profileFragment, profileFragment.tag).hide(profileFragment)
            .commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()
        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.actionHome) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                    .commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.actionCart) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment)
                    .commit()
                activeFragment = cartFragment
            } else if (it.itemId == R.id.actionFavorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            } else if (it.itemId == R.id.actionProfile) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                    .commit()
                activeFragment = profileFragment
            }
            return@setOnNavigationItemSelectedListener true
        }

        btnMenu.setOnClickListener {
            val fragment = ChangeLanguageFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        loadData()
    }

    fun loadData(){
        viewModel.getTopProducts()
        viewModel.getCategories()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}