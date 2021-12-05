package dev.frankperez.projectlovemyplanet

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import dev.frankperez.projectlovemyplanet.databinding.ActivityPrincipalBinding
import kotlinx.android.synthetic.main.nav_header_principal.*

class PrincipalActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPrincipal.toolbar)
        binding.appBarPrincipal.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_principal)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_registrar_actividades,
                R.id.nav_listar_actividades, R.id.nav_canjear,
                R.id.nav_suscribir_actividad, R.id.nav_main
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    mostrarTitulos()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.principal, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_salir) finish()
        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_principal)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    public fun mostrarTitulos(){
        findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<TextView>(R.id.lblAppName).setText(GlobalClass.nombres+ " " + GlobalClass.apellidos + " : "+GlobalClass.saldoPuntos+ " points")
        findViewById<NavigationView>(R.id.nav_view).getHeaderView(0).findViewById<TextView>(R.id.lblAppSubTitle).setText(GlobalClass.Tipo+ " - "+GlobalClass.email)
    }
}