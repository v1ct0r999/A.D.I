package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class BusquedaCategoriaActivity extends AppCompatActivity {

    private LinearLayout layoutCategorias;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_categoria);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        layoutCategorias = findViewById(R.id.layout_categorias);
        db = FirebaseFirestore.getInstance();

        // Cargar las categorías desde Firebase
        loadCategorias();
    }

    private void loadCategorias() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // UID del usuario autenticado

        db.collection("usuarios").document(userId).collection("categorias")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    layoutCategorias.removeAllViews(); // Limpia los datos previos
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String categoriaNombre = doc.getString("nombre");
                        if (categoriaNombre != null) {
                            createButtonForCategoria(categoriaNombre);
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error cargando categorías", Toast.LENGTH_SHORT).show());
    }

    private void createButtonForCategoria(String nombre) {
        Button button = new Button(this);
        button.setText(nombre);
        button.setAllCaps(false); // Evitar texto en mayúsculas
        button.setPadding(16, 16, 16, 16);

        // Listener para manejar clics en cada categoría
        button.setOnClickListener(v -> {
            Toast.makeText(this, "Seleccionaste: " + nombre, Toast.LENGTH_SHORT).show();

            // Crear un Intent para abrir la actividad de productos por categoría
            Intent intent = new Intent(BusquedaCategoriaActivity.this, ProductosPorCategoriaActivity.class);
            intent.putExtra("nombreCategoria", nombre); // Pasar el nombre de la categoría
            startActivity(intent); // Iniciar la nueva actividad
        });

        layoutCategorias.addView(button); // Agregar el botón a la interfaz
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Volver a la pantalla anterior
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
