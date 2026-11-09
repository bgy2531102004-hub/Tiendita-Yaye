package com.example.tiendita

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.tiendita.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        // 1. Configurar el Executor y el BiometricPrompt
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "¡Huella validada con éxito!", Toast.LENGTH_SHORT).show()

                    // Acción al pasar la huella correctamente (p. ej., ir al panel de control)
                    val intento = Intent(this@MainActivity, PanelControl::class.java)
                    startActivity(intento)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "No se reconoció la huella. Intente de nuevo.", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Inicio de sesión biométrico")
            .setSubtitle("Inicia sesión usando tus credenciales biométricas")
            .setNegativeButtonText("Cancelar")
            .build()

        // 2. Botón Registrar
        binding.btnRegistrar.setOnClickListener {
            val intento = Intent(this, Registrar::class.java)
            startActivity(intento)
        }

        // 3. Botón Iniciar Sesión
        binding.btnIniciarSesion.setOnClickListener {
            val correo = binding.etCorreo.text.toString().trim()
            val password = binding.etContrasena.text.toString().trim()

            if (correo.isNotEmpty() && password.isNotEmpty()) {
                // Primero validamos las credenciales en Firebase
                loginUser(correo, password)
            } else {
                Toast.makeText(this, "Por favor ingrese correo y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(mail: String, password: String) {
        auth.signInWithEmailAndPassword(mail, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Credenciales correctas. Confirme su huella.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Una vez que Firebase valida el correo/contraseña, lanzamos la huella digital como segundo factor
                    biometricPrompt.authenticate(promptInfo)

                } else {
                    Toast.makeText(
                        baseContext,
                        "Error de autenticación: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}