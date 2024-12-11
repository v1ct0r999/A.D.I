package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;


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
    private List<String> productList;
    private ArrayAdapter<String> adapter;

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

        editTextSearch = findViewById(R.id.editTextSearch);
        listViewProductos = findViewById(R.id.listViewProductos);
        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();

        // **Asegúrate de crear el adaptador aquí antes de cargar los productos**
        ProductoAdapter adapter = new ProductoAdapter(this, productList);
        listViewProductos.setAdapter(adapter);

        // Cargar los productos
        loadProductos(adapter);  // Pasa el adaptador como parámetro para notificar los cambios

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

    private void loadProductos(ProductoAdapter adapter) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // UID del usuario autenticado

        db.collection("usuarios").document(userId).collection("productos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    productList.clear(); // Limpiar la lista antes de agregar nuevos datos
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String productName = doc.getString("nombre");
                        if (productName != null) {
                            productList.add(productName);
                        }
                    }
                    // Notificar al adaptador después de que los datos se hayan cargado
                    adapter.notifyDataSetChanged(); // Notificar al adaptador sobre los cambios
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show());
    }
}
