package cl.vmi.adi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;

public class ProductoAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> productos;

    public ProductoAdapter(Context context, List<String> productos) {
        super(context, R.layout.item_producto, productos);
        this.context = context;
        this.productos = productos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_producto, parent, false);
        }

        // Obtener la vista para el nombre del producto
        TextView productNameTextView = convertView.findViewById(R.id.textViewProductName);
        Button buttonDetalle = convertView.findViewById(R.id.buttonDetalle);
        Button buttonRestar = convertView.findViewById(R.id.buttonRestar);

        // Obtener el nombre del producto en esta posición
        String productName = productos.get(position);
        productNameTextView.setText(productName);

        // Agregar acción a los botones
        buttonDetalle.setOnClickListener(v -> {
            // Lógica para el primer botón (Acción 1)
            // Puedes hacer cualquier acción aquí, por ejemplo, mostrar un Toast
        });

        buttonRestar.setOnClickListener(v -> {
            // Lógica para el segundo botón (Acción 2)
            // Puedes hacer cualquier acción aquí, por ejemplo, mostrar un Toast
        });

        return convertView;
    }
}
