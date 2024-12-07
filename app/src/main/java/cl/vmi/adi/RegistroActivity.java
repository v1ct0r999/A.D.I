package cl.vmi.adi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistroActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonRegister, buttonGoToLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        auth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonGoToLogin = findViewById(R.id.buttonGoToLogin);

        buttonRegister.setOnClickListener(view -> registerUser());
        buttonGoToLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el correo y la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación de la contraseña
        if (!isValidPassword(password)) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, números y caracteres especiales.", Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // Manejar errores de Firebase y traducir al español
                        if (task.getException() != null) {
                            String exceptionMessage = task.getException().getMessage();

                            if (exceptionMessage.contains("The email address is badly formatted")) {
                                Toast.makeText(RegistroActivity.this, "El correo electrónico no tiene un formato válido.", Toast.LENGTH_SHORT).show();
                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegistroActivity.this, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegistroActivity.this, "Error en el registro: " + exceptionMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    // Método para validar la contraseña
    private boolean isValidPassword(String password) {
        // Asegurarse de que la contraseña tiene al menos 8 caracteres
        if (password.length() < 8) {
            return false;
        }
        // Verificar si la contraseña contiene al menos una letra mayúscula, una minúscula, un número y un carácter especial
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(RegistroActivity.this, MainActivity.class));
            finish();
        }
    }
}
