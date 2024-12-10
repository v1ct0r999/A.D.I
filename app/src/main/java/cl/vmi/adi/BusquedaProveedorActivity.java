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

public class BusquedaProveedorActivity extends AppCompatActivity {

    private LinearLayout layoutProveedores;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_proveedor);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        layoutProveedores = findViewById(R.id.layout_proveedores);
        db = FirebaseFirestore.getInstance();

        // Cargar los proveedores desde Firebase
        loadProveedores();
    }

    private void loadProveedores() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // UID del usuario autenticado

        db.collection("usuarios").document(userId).collection("proveedores")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    layoutProveedores.removeAllViews(); // Limpia los datos previos
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String proveedorNombre = doc.getString("nombre");
                        if (proveedorNombre != null) {
                            createButtonForProveedor(proveedorNombre);
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error cargando proveedores", Toast.LENGTH_SHORT).show());
    }

    private void createButtonForProveedor(String nombre) {
        Button button = new Button(this);
        button.setText(nombre);
        button.setAllCaps(false); // Evitar texto en mayúsculas
        button.setPadding(16, 16, 16, 16);

        // Listener para manejar clics en cada proveedor
        button.setOnClickListener(v -> {
            Toast.makeText(this, "Seleccionaste: " + nombre, Toast.LENGTH_SHORT).show();

            // Crear un Intent para abrir ProductosPorProveedorActivity
            Intent intent = new Intent(BusquedaProveedorActivity.this, ProductosPorProveedorActivity.class);
            intent.putExtra("nombreProveedor", nombre); // Pasar el nombre del proveedor
            startActivity(intent); // Iniciar la actividad
        });

        layoutProveedores.addView(button);
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
