package cl.vmi.adi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;

public class ProductoAdapter extends ArrayAdapter<Producto> {

    private Context context;
    private List<Producto> productos;

    // Constructor del adaptador
    public ProductoAdapter(Context context, List<Producto> productos) {
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

        // Obtener las vistas de los elementos en el layout
        TextView productNameTextView = convertView.findViewById(R.id.textViewProductName);
        Button buttonDetalle = convertView.findViewById(R.id.buttonDetalle);
        Button buttonRestar = convertView.findViewById(R.id.buttonRestar);

        // Obtener el objeto Producto en esta posición
        Producto producto = productos.get(position);
        productNameTextView.setText(producto.getNombre());

        // Agregar acción al botón "Detalle"
        buttonDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para abrir la actividad de detalles
                Intent intent = new Intent(context, DetalleProductoActivity.class);

                // Pasar los datos del producto a la actividad de detalles
                intent.putExtra("nombre", producto.getNombre());
                intent.putExtra("categoria", producto.getCategoria());
                intent.putExtra("proveedor", producto.getProveedor());
                intent.putExtra("stock", producto.getStock());
                intent.putExtra("valorUnitario", producto.getValorUnitario());

                // Iniciar la actividad de detalle
                context.startActivity(intent);
            }
        });

        // Agregar acción al botón "Restar" (puedes agregar la lógica que necesites aquí)
        buttonRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para restar stock o lo que desees hacer
            }
        });

        return convertView;
    }
}

