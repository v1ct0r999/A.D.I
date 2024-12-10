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

public class ProductosPorProveedorActivity extends AppCompatActivity {

    private TextView textViewProveedor;
    private ListView listViewProductos;
    private FirebaseFirestore db;
    private List<String> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_por_proveedor);

        textViewProveedor = findViewById(R.id.textViewProveedor);
        listViewProductos = findViewById(R.id.listViewProductos);

        db = FirebaseFirestore.getInstance();
        productList = new ArrayList<>();

        // Recibir el nombre del proveedor desde el intent
        String proveedorNombre = getIntent().getStringExtra("nombreProveedor");

        if (proveedorNombre != null) {
            textViewProveedor.setText("Proveedor: " + proveedorNombre);
            loadProductosPorProveedor(proveedorNombre);
        } else {
            Toast.makeText(this, "Error al cargar el proveedor", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadProductosPorProveedor(String proveedorNombre) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Consultar los productos asociados al proveedor
        db.collection("usuarios").document(userId).collection("productos")
                .whereEqualTo("proveedor", proveedorNombre)
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
