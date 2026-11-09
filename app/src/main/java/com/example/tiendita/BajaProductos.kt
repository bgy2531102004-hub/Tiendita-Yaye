package com.example.tiendita

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendita.databinding.ActivityAltaProductosBinding
import com.example.tiendita.databinding.ActivityBajaProductosBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class BajaProductos : AppCompatActivity() {

    private lateinit var binding : ActivityBajaProductosBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBajaProductosBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnBuscarBaja.setOnClickListener {
            val codigo = binding.etCodigoBaja.text.toString()

            db.collection("Productos").document(codigo)
                .get().addOnSuccessListener {
                    binding.tvNombreDetalle.setText(it.get("Nombre") as String?)
                    binding.tvCantidadDetalle.setText(it.get("Cantidad") as String?)
                    binding.tvPrecioDetalle.setText(it.get("Precio") as String?)
                    binding.tvCategoriaDetalle.setText(it.get("Categoria") as String?)
                }

        }
        binding.btnEliminarDefinitivo.setOnClickListener {
            val codigo = binding.etCodigoBaja.text.toString()
            db.collection("Productos"). document(codigo).delete()

        }
        binding.btnCancelarBaja.setOnClickListener {
            val intento = Intent(this, PanelControl::class.java)
            startActivity(intento)
        }

    }
}