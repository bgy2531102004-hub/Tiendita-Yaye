package com.example.tiendita
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendita.data.Productos
import com.example.tiendita.databinding.ActivityPanelControlBinding
import com.example.tiendita.ui.adapter.ProductosAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firestore.v1.Document

class PanelControl : AppCompatActivity() {

    private lateinit var binding: ActivityPanelControlBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private lateinit var productosAdapter: ProductosAdapter
    private val listaProductos = mutableListOf<Productos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPanelControlBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        binding.rvPanelControl.layoutManager = LinearLayoutManager(this)
        productosAdapter = ProductosAdapter(listaProductos)
        binding.rvPanelControl.adapter = productosAdapter
        cargarProductos()


        binding.btnAltaPanel.setOnClickListener {
            val intento = Intent(this, AltaProductos::class.java)
            startActivity(intento)
        }
        binding.btnBajaPanel.setOnClickListener {
            val intento = Intent(this, BajaProductos::class.java)
            startActivity(intento)

        }
        binding.btnBuscarPanel.setOnClickListener {
            val intento = Intent(this, BusquedaProductos::class.java)
            startActivity(intento)
        }
        binding.btnSalir.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }
    }
    private fun cargarProductos() {
        db.collection("Productos").get()
            .addOnSuccessListener { resultado ->

                for (documento in resultado) {
                    val id = documento.id
                    val nombre = documento.getString("Nombre") ?: ""
                    val categoria = documento.getString("Categoria") ?: ""
                    val precio = documento.getString("Precio") ?: ""
                    val cantidad = documento.getString("Cantidad") ?: ""

                    val producto = Productos(
                        id_producto = id,
                        nombre = nombre,
                        precio = precio,
                        cantidad = cantidad,
                        categoria = categoria
                    )
                    listaProductos.add(producto)
                }
                productosAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show()
            }
    }
}