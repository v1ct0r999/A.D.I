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

public class ListaProductosActivity extends AppCompatActivity {

    private ListView listViewProductos;
    private FirebaseFirestore db;
    private List<String> productoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        //listViewProductos = findViewById(R.id.listViewProductos);
        db = FirebaseFirestore.getInstance();
        productoList = new ArrayList<>();

        loadProductos();
    }

    private void loadProductos() {
        db.collection("productos")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    productoList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        productoList.add(doc.getString("nombre"));
                    }
                    // Usa un adaptador para mostrar la lista
                    listViewProductos.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_black_text, productoList));
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show());
    }

}