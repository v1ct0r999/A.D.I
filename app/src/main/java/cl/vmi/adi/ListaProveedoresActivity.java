package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import java.util.List;

public class ListaProveedoresActivity extends AppCompatActivity {

    private ListView listViewProveedores;
    private Button buttonAgregarProveedor;
    private FirebaseFirestore db;
    private List<String> proveedorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proveedores);

        listViewProveedores = findViewById(R.id.listViewProveedores);
        buttonAgregarProveedor = findViewById(R.id.buttonAgregarProveedor);
        db = FirebaseFirestore.getInstance();
        proveedorList = new ArrayList<>();

        buttonAgregarProveedor.setOnClickListener(view -> {
            startActivity(new Intent(ListaProveedoresActivity.this, AgregarProveedoresActivity.class));
        });

        loadProveedores();
    }

    private void loadProveedores() {
        db.collection("proveedores")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    proveedorList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        proveedorList.add(doc.getString("nombre"));
                    }
                    // Usa un adaptador para mostrar la lista
                    listViewProveedores.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_black_text, proveedorList));
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error cargando categorías", Toast.LENGTH_SHORT).show());
    }

}