package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private Button buttonCategoria, buttonProveedor, buttonProducto, buttonLogout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        welcomeMessage = findViewById(R.id.welcome_message);
        buttonCategoria = findViewById(R.id.buttonCategoria);
        buttonProveedor = findViewById(R.id.buttonProveedor);
        buttonProducto = findViewById(R.id.buttonProducto);
        buttonLogout = findViewById(R.id.buttonLogout);

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            welcomeMessage.setText("Bienvenido, " + user.getEmail());
        }

        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListaCategoriasActivity.class));
            }
        });

        buttonProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListaProveedoresActivity.class));
            }
        });

        buttonProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AgregarProductosActivity.class));
            }
        });


        buttonLogout.setOnClickListener(view -> logoutUser());
    }

    private void logoutUser() {
        auth.signOut();
        Toast.makeText(MainActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
