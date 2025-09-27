package com.example.galeriaudb

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galeriaudb.R
import com.example.galeriaudb.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    // RecyclerView que mostrará la lista de fotos
    private lateinit var recycler: RecyclerView

    // Barra de carga para indicar que se están descargando los datos
    private lateinit var loader: ProgressBar

    // Texto que se muestra en caso de error
    private lateinit var tvError: TextView

    // Layout para refrescar con swipe hacia abajo
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    // Adaptador del RecyclerView
    private val adapter = FotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recyclerView)
        loader = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            cargarFotos()
        }

        cargarFotos()
    }

    private fun cargarFotos() {
        mostrarCargando() // mostramos loader mientras se hace la petición
        lifecycleScope.launch {
            // Ejecutamos la llamada a la API en un hilo de fondo (IO)
            try {
                val fotos = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.getFotos()
                }
                mostrarExito(fotos) //si todo sale bien, mostramos las fotos
            } catch (e: Exception) {
                // Si ocurre un error, lo mostramos en pantalla
                mostrarError("Error: ${e.localizedMessage}")
            } finally {
                // Se desactiva la animación del SwipeRefresh
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun mostrarCargando() {
        loader.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        recycler.visibility = View.GONE
    }

    private fun mostrarExito(lista: List<com.example.galeriaudb.model.Foto>) {
        loader.visibility = View.GONE
        tvError.visibility = View.GONE
        recycler.visibility = View.VISIBLE
        adapter.actualizar(lista)
    }

    private fun mostrarError(msg: String) {
        loader.visibility = View.GONE
        recycler.visibility = View.GONE
        tvError.text = msg
        tvError.visibility = View.VISIBLE
    }
}