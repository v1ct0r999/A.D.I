package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private FirebaseAuth auth;
    private Button buttonCategoria, buttonProveedor, buttonProducto, buttonReporte, buttonInventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura el Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicialización de FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Enlazando los botones
        buttonCategoria = findViewById(R.id.buttonCategoria);
        buttonProveedor = findViewById(R.id.buttonProveedor);
        buttonProducto = findViewById(R.id.buttonProducto);
        buttonReporte = findViewById(R.id.buttonReporte);
        buttonInventario = findViewById(R.id.buttonInventario);

        // BOTON PRODUCTO
        buttonProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, AgregarProductosActivity.class));

            }
        });

        // BOTON PROVEEDOR
        buttonProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ListaProveedoresActivity.class));

            }
        });

        // BOTON CATEGORIA
        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ListaCategoriasActivity.class));

            }
        });

        // BOTON REPORTE
        buttonReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(MainActivity.this, ReporteActivity.class));

            }
        });

        // BOTON REPORTE
        buttonInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(MainActivity.this, InventarioActivity.class));

            }
        });

        // Obtener usuario actual
        //FirebaseUser user = auth.getCurrentUser();
        //if (user != null) {
        //    welcomeMessage.setText("Bienvenido, " + user.getEmail());
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Agrega los ítems al menú
        MenuItem itemCerrar = menu.add(Menu.NONE, 0, Menu.NONE, "Cerrar sesión");
        itemCerrar.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);  // Menú desplegable

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0: // Cerrar sesión
                logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logoutUser() {
        auth.signOut();
        Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}

