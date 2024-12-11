package cl.vmi.adi;

public class Producto {

    private String nombre;
    private String categoria;
    private String proveedor;
    private String stock;  // Se mantiene como String
    private String valorUnitario;  // Cambié el nombre de precioUnitario a valorUnitario

    // Constructor vacío requerido por Firebase
    public Producto() {}

    // Constructor con parámetros (todos los datos como String)
    public Producto(String nombre, String categoria, String proveedor, String stock, String valorUnitario) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.stock = stock;
        this.valorUnitario = valorUnitario;  // Usamos valorUnitario en lugar de precioUnitario
    }

    // Getter y Setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter para 'categoria'
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Getter y Setter para 'proveedor'
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    // Getter y Setter para 'stock' (se mantiene como String)
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    // Getter y Setter para 'valorUnitario' (antes 'precioUnitario')
    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}