package com.example.tiendita

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendita.databinding.ActivityAltaProductosBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AltaProductos : AppCompatActivity() {

    private lateinit var binding : ActivityAltaProductosBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAltaProductosBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnGuardarProducto.setOnClickListener {
            val codigo = binding.etCodigo.text.toString()
            val nombre = binding.etNombreProducto.text.toString()
            val cantidad = binding.etCantidadProducto.text.toString()
            val precio = binding.etPrecio.text.toString()
            val categoria = binding.etCategoria.text.toString()

            db.collection("Productos").document(codigo)
                .set(hashMapOf("Nombre" to nombre, "Cantidad" to cantidad, "Precio" to precio, "Categoria" to categoria))

            Toast.makeText(this, "Producto Guardado", Toast.LENGTH_SHORT).show()


        }
        binding.btnCancelarAlta.setOnClickListener {
            val intento = Intent(this, PanelControl::class.java)
            startActivity(intento)
        }
    }
}