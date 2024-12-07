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
import com.google.firebase.auth.FirebaseAuth;

public class ListaCategoriasActivity extends AppCompatActivity {

    private ListView listViewCategories;
    private Button buttonAddCategory;
    private FirebaseFirestore db;
    private List<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categorias);

        listViewCategories = findViewById(R.id.listViewCategories);
        buttonAddCategory = findViewById(R.id.buttonAddCategory);
        db = FirebaseFirestore.getInstance();
        categoryList = new ArrayList<>();

        buttonAddCategory.setOnClickListener(view -> {
            startActivity(new Intent(ListaCategoriasActivity.this, AgregarCategoriasActivity.class));
        });

        loadCategories();
    }

    private void loadCategories() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Obtener el UID del usuario autenticado

        db.collection("usuarios").document(userId).collection("categorias") // Consultar la subcolección bajo el UID del usuario
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    categoryList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        categoryList.add(doc.getString("nombre"));
                    }
                    // Usa un adaptador para mostrar la lista
                    listViewCategories.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_black_text, categoryList));
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error cargando categorías", Toast.LENGTH_SHORT).show());
    }
}
