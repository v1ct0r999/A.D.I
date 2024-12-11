package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TodosProductosActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private ListView listViewProductos;
    private FirebaseFirestore db;
    private List<Producto> productList;  // Lista de productos
    private ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_productos);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inicializar vistas
        editTextSearch = findViewById(R.id.editTextSearch);
        listViewProductos = findViewById(R.id.listViewProductos);
        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();

        // Crear el adaptador con la lista de productos
        adapter = new ProductoAdapter(this, productList);
        listViewProductos.setAdapter(adapter);

        // Cargar los productos desde Firestore
        loadProductos();

        // Configurar filtro de búsqueda
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s); // Filtrar la lista según el texto ingresado
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Método para cargar los productos desde Firestore
    private void loadProductos() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // UID del usuario autenticado

        db.collection("usuarios").document(userId).collection("productos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    productList.clear(); // Limpiar la lista antes de agregar nuevos datos
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String productoId = doc.getId();  // Obtener el ID generado por Firebase
                        String nombre = doc.getString("nombre");
                        String categoria = doc.getString("categoria");
                        String proveedor = doc.getString("proveedor");

                        // Manejar stock (puede ser un número, así que lo convertimos a String)
                        Object stockObj = doc.get("stock");
                        String stock = (stockObj != null) ? stockObj.toString() : "0";  // Convertir a String

                        // Manejar valorUnitario (antes precioUnitario, lo convertimos a String)
                        Object valorUnitarioObj = doc.get("valorUnitario");  // Cambié el nombre a "valorunitario"
                        String valorUnitario = (valorUnitarioObj != null) ? valorUnitarioObj.toString() : "0";

                        // Crear un nuevo objeto Producto con los datos obtenidos
                        Producto producto = new Producto(nombre, categoria, proveedor, stock, valorUnitario);
                        producto.setId(productoId);  // Asignar el ID del producto

                        // Agregar el producto a la lista
                        productList.add(producto);
                    }
                    // Notificar al adaptador después de que los datos se hayan cargado
                    adapter.notifyDataSetChanged(); // Notificar al adaptador sobre los cambios
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Volver a la pantalla principal (MainActivity)
            startActivity(new Intent(this, InventarioActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}