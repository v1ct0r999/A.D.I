package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalleProductoActivity extends AppCompatActivity {

    private TextView textViewNombre;
    private TextView textViewCategoria;
    private TextView textViewProveedor;
    private TextView textViewStock;
    private TextView textViewValorUnitario;  // Cambié de precioUnitario a valorUnitario
    private Button buttonEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        // Inicializar las vistas
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewCategoria = findViewById(R.id.textViewCategoria);
        textViewProveedor = findViewById(R.id.textViewProveedor);
        textViewStock = findViewById(R.id.textViewStock);
        textViewValorUnitario = findViewById(R.id.textViewValorUnitario);  // Aquí cambiamos también el ID
        buttonEliminar = findViewById(R.id.buttonEliminar);

        // Recibir los datos del Intent
        Intent intent = getIntent();

        // Asegurarse de que los valores no sean nulos antes de mostrarlos
        String nombre = intent.getStringExtra("nombre");
        String categoria = intent.getStringExtra("categoria");
        String proveedor = intent.getStringExtra("proveedor");
        String stock = intent.getStringExtra("stock");
        String valorUnitario = intent.getStringExtra("valorUnitario");  // Cambié de precioUnitario a valorUnitario
        String productoId = intent.getStringExtra("productoId");  // Recibimos el ID del producto

        // Si alguno de los valores es nulo, asignar un valor por defecto o mostrar un mensaje
        if (nombre == null) nombre = "No disponible";
        if (categoria == null) categoria = "No disponible";
        if (proveedor == null) proveedor = "No disponible";
        if (stock == null) stock = "No disponible";
        if (valorUnitario == null) valorUnitario = "No disponible";  // Cambié de precioUnitario a valorUnitario

        // Mostrar los datos en los TextViews correspondientes
        textViewNombre.setText(nombre);
        textViewCategoria.setText(categoria);
        textViewProveedor.setText(proveedor);
        textViewStock.setText(stock);
        textViewValorUnitario.setText(valorUnitario);  // Cambié de precioUnitario a valorUnitario

        // Acción para el botón "Eliminar"
        buttonEliminar.setOnClickListener(v -> {
            if (productoId != null && !productoId.isEmpty()) {
                // Lógica para eliminar el producto
                eliminarProducto(productoId);
            } else {
                Toast.makeText(DetalleProductoActivity.this, "ID de producto no disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para eliminar el producto
    private void eliminarProducto(String productoId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // UID del usuario autenticado

        // Intentamos eliminar el producto de Firestore usando el productoId
        db.collection("usuarios").document(userId).collection("productos")
                .document(productoId)  // Usar el productoId que es el ID de Firestore
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Producto eliminado correctamente
                    Toast.makeText(DetalleProductoActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    finish();  // Volver a la actividad anterior (si es necesario)
                })
                .addOnFailureListener(e -> {
                    // Si ocurre un error al eliminar el producto
                    Toast.makeText(DetalleProductoActivity.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                });
    }
}
