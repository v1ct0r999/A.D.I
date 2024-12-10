package cl.vmi.adi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductosPorCategoriaActivity extends AppCompatActivity {

    private TextView textViewCategoria;
    private ListView listViewProductos;
    private FirebaseFirestore db;
    private List<String> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_por_categoria);

        textViewCategoria = findViewById(R.id.textViewCategoria);
        listViewProductos = findViewById(R.id.listViewProductos);

        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();

        // Recibir el nombre de la categoría desde el intent
        String categoriaNombre = getIntent().getStringExtra("nombreCategoria");

        if (categoriaNombre != null) {
            textViewCategoria.setText("Categoría: " + categoriaNombre);
            loadProductosPorCategoria(categoriaNombre);
        } else {
            Toast.makeText(this, "Error al cargar la categoría", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadProductosPorCategoria(String categoriaNombre) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Consultar los productos asociados a la categoría
        db.collection("usuarios").document(userId).collection("productos")
                .whereEqualTo("categoria", categoriaNombre)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    productList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        productList.add(doc.getString("nombre"));
                    }
                    // Mostrar los productos en el ListView
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
                    listViewProductos.setAdapter(adapter);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show());
    }
}
