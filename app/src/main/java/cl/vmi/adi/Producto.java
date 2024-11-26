package cl.vmi.adi;

public class Producto {
    private String nombre;


    // Constructor vacío requerido por Firebase
    public Producto() {}

    // Constructor que inicializa el nombre
    public Producto(String nombre) {
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
