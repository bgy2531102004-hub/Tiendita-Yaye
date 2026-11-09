package com.example.tiendita

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendita.databinding.ActivityRegistrarBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Registrar : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        binding.tvVolverLogin.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }

        binding.btnRegistrarse.setOnClickListener {
            val correo = binding.etCorreo2.text.toString()
            val pass1 = binding.etPassword2.text.toString()
            val pass2 = binding.etRepeatPassword.text.toString()
            if (correo.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass1 != pass2) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(correo, pass1)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                         val intento = Intent(this, MainActivity::class.java)
                        startActivity(intento)
                        finish()
                    } else {
                        Toast.makeText(this, "No se completo el registro", Toast.LENGTH_SHORT).show()

                           }
                }
        }
    }

}