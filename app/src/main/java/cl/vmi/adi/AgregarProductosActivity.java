package cl.vmi.adi;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgregarProductosActivity extends AppCompatActivity {

    private EditText editTextProductName, editTextStock, editTextUnitPrice;
    private Spinner spinnerCategory, spinnerProveedor;
    private Button buttonSaveProduct;
    private FirebaseFirestore db;

    private List<String> categoryList = new ArrayList<>();
    private List<String> proveedorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos);

        // Inicializar los componentes
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextStock = findViewById(R.id.editTextStock);
        editTextUnitPrice = findViewById(R.id.editTextUnitPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerProveedor = findViewById(R.id.spinnerProveedor);
        buttonSaveProduct = findViewById(R.id.buttonSaveProduct);

        db = FirebaseFirestore.getInstance();

        // Cargar categorías y proveedores para los spinners
        loadCategories();
        loadProveedores();

        // Configurar botón de guardar
        buttonSaveProduct.setOnClickListener(v -> saveProduct());
    }

    private void loadCategories() {
        db.collection("categorias").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                categoryList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String categoryName = document.getString("nombre");
                    if (categoryName != null) {
                        categoryList.add(categoryName);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_black_text, categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Error al cargar categorías", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProveedores() {
        db.collection("proveedores").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                proveedorList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String proveedorName = document.getString("nombre");
                    if (proveedorName != null) {
                        proveedorList.add(proveedorName);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_black_text, proveedorList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProveedor.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Error al cargar proveedores", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProduct() {
        String productName = editTextProductName.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String proveedor = spinnerProveedor.getSelectedItem().toString();
        String stockStr = editTextStock.getText().toString().trim();
        String unitPriceStr = editTextUnitPrice.getText().toString().trim();

        if (productName.isEmpty() || stockStr.isEmpty() || unitPriceStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int stock = Integer.parseInt(stockStr);
        double unitPrice = Double.parseDouble(unitPriceStr);

        // Crear un mapa para los datos del producto
        Map<String, Object> product = new HashMap<>();
        product.put("nombre", productName);
        product.put("categoria", category);
        product.put("proveedor", proveedor);
        product.put("stock", stock);
        product.put("valorUnitario", unitPrice);

        // Guardar el producto en Firestore
        db.collection("productos").add(product)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Producto guardado con éxito", Toast.LENGTH_SHORT).show();
                    finish(); // Cerrar la actividad
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar el producto", Toast.LENGTH_SHORT).show());
    }
}
