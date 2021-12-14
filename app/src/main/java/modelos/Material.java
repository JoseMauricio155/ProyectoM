package modelos;

public class Material {
    int id;
    String Nombre;
    double Precio;

    public Material(int id, String nombre, double precio) {
        this.id = id;
        Nombre = nombre;
        Precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return Nombre;
    }

    public double getPrecio() {
        return Precio;
    }
}
