package net.braniumacademy.chapter14.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import net.braniumacademy.chapter14.R
import net.braniumacademy.chapter14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    // todo task 1 -->: do something...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // lấy tham chiếu navController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // todo end task 1 <--

        // todo task 2: do something...
        // thiết lập bottom navigation
//        val bottomNavigationView =
//            findViewById<BottomNavigationView>(R.id.bottom_nav)
//        bottomNavigationView?.setupWithNavController(navController)

        binding.includeMainLayout.bottomNav?.setupWithNavController(navController)

        // todo end task 2

        // lấy tham chiếu tới drawer layout nếu nó tồn tại
        // thiết lập nút Up và 3 màn hình chính của 3 tab và navigation drawer
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home_fragment, R.id.register_fragment, R.id.setting_fragment),
            drawerLayout = binding.drawerLayout
        )
        // gắn navigation view vào navController nếu navigation view tồn tại:
        binding.navView?.setupWithNavController(navController)

        // thiết lập toolbar làm action bar nếu toolbar tồn tại
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar?.setupWithNavController(navController, appBarConfiguration)

        // thiết lập navigation rail nếu nó tồn tại
        binding.navRail?.setupWithNavController(navController)

        onDestinationChanged()
        setUpDrawerListener(binding.drawerLayout)
        setUpNavigationDrawerItemSelected()
    }

    // lắng nghe sự kiện trang đích thay đổi
    private fun onDestinationChanged() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment -> {
                    // do something if destination is home fragment
                }

                R.id.register_fragment -> {
                    // do something if destination is register fragment
                }

                R.id.setting_fragment -> {
                    // do something if destination is setting fragment
                }

                else -> {
                    // do something
                }
            }
        }
    }

    // bắt sự kiện navigation drawer mở, đóng, trượt, thay đổi trạng thái
    private fun setUpDrawerListener(drawerLayout: DrawerLayout?) {
        drawerLayout?.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // todo
            }

            override fun onDrawerOpened(drawerView: View) {
                // todo
            }

            override fun onDrawerClosed(drawerView: View) {
                // todo
            }

            override fun onDrawerStateChanged(newState: Int) {
                // todo
            }
        })
    }

    // lắng nghe sự kiện một phần tử trong menu navigation drawer được click
    private fun setUpNavigationDrawerItemSelected() {
        binding.navView?.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_drawer_search -> {
                    // todo
                }

                R.id.menu_item_drawer_about -> {
                    // todo
                }

                R.id.menu_item_drawer_import -> {
                    // todo
                }

                // ...
            }
            false
        }
    }

    // cho phép bắt sự kiến nhấn Back
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}