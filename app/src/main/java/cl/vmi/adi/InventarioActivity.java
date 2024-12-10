package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InventarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        // Configurar el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Referencias a los botones
        Button btnProveedor = findViewById(R.id.btn_proveedor);
        Button btnCategoria = findViewById(R.id.btn_categoria);
        Button btnProductos = findViewById(R.id.btn_productos);

        // Listeners para las opciones
        btnProveedor.setOnClickListener(v -> {
            Intent intent = new Intent(InventarioActivity.this, BusquedaProveedorActivity.class);
            startActivity(intent);
        });

        btnCategoria.setOnClickListener(v -> {
            Intent intent = new Intent(InventarioActivity.this, BusquedaCategoriaActivity.class);
            startActivity(intent);
        });

        btnProductos.setOnClickListener(v -> {
            Intent intent = new Intent(InventarioActivity.this, TodosProductosActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Volver a la pantalla principal (MainActivity)
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
