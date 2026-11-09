package com.example.tiendita

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendita.databinding.ActivityAltaProductosBinding
import com.example.tiendita.databinding.ActivityBusquedaProductosBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class BusquedaProductos : AppCompatActivity() {

    private lateinit var binding : ActivityBusquedaProductosBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBusquedaProductosBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBuscarAccion.setOnClickListener {
            val codigo = binding.etCodigoBusqueda.text.toString()

            db.collection("Productos").document(codigo)
                .get().addOnSuccessListener {
                    binding.etEditNombre.setText(it.get("Nombre") as String?)
                    binding.etEditStock.setText(it.get("Cantidad") as String?)
                    binding.etEditPrecio.setText(it.get("Precio") as String?)
                    binding.etEditCategoria.setText(it.get("Categoria") as String?)
                }
        }
        binding.btnActualizar.setOnClickListener {
            val codigo = binding.etCodigoBusqueda.text.toString()
            val nuevoNombre = binding.etEditNombre.text.toString()
            val nuevaCantidad = binding.etEditStock.text.toString()
            val nuevoPrecio = binding.etEditPrecio.text.toString()
            val nuevaCategoria = binding.etEditCategoria.text.toString()

            db.collection("Productos").document(codigo)
                .set(hashMapOf("Nombre" to nuevoNombre, "Cantidad" to nuevaCantidad, "Precio" to nuevoPrecio, "Categoria" to nuevaCategoria))
                .addOnSuccessListener {
                    Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
                }
        }
        binding.btnCancelarBusqueda.setOnClickListener {
            val intento = Intent(this, PanelControl::class.java)
            startActivity(intento)
        }


    }

}
