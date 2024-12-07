package cl.vmi.adi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

public class AgregarCategoriasActivity extends AppCompatActivity {

    private EditText editTextCategoryName;
    private Button buttonSaveCategory;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categorias);

        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        buttonSaveCategory = findViewById(R.id.buttonSaveCategory);
        db = FirebaseFirestore.getInstance();

        buttonSaveCategory.setOnClickListener(view -> saveCategory());
    }

    private void saveCategory() {
        String name = editTextCategoryName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre de la categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Obtener UID del usuario autenticado

        // Guardar la categoría bajo el UID del usuario
        db.collection("usuarios").document(userId).collection("categorias")
                .add(new Category(name))
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Categoría guardada", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show());
    }

}
