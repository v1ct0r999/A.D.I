package cl.vmi.adi;

public class Category {
    private String nombre;

    // Constructor vacío requerido por Firebase
    public Category() {}

    // Constructor que inicializa el nombre
    public Category(String nombre) {
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
