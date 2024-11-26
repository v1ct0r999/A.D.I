package cl.vmi.adi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class AgregarProveedoresActivity extends AppCompatActivity {

    private EditText editTextProveedorName;
    private Button buttonSaveProveedor;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_proveedores);

        editTextProveedorName = findViewById(R.id.editTextProveedorName);
        buttonSaveProveedor = findViewById(R.id.buttonSaveProveedor);
        db = FirebaseFirestore.getInstance();

        buttonSaveProveedor.setOnClickListener(view -> saveProveedor());
    }

    private void saveProveedor() {
        String name = editTextProveedorName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del proveedor", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("proveedores").add(new Proveedor(name))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Proveedor guardado", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show());
    }
}
