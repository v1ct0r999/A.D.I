package cl.vmi.adi;

public class Proveedor {
    private String nombre;

    // Constructor vacío requerido por Firebase
    public Proveedor() {}

    // Constructor que inicializa el nombre
    public Proveedor(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}