package modelos;

public class Descripcion {
    String descripcion;
    double largo, ancho, grosor,  pies;
    int cantidad;

    public Descripcion(String descripcion, double largo, double ancho, double grosor, int cantidad, double pies) {
        this.descripcion = descripcion;
        this.largo = largo;
        this.ancho = ancho;
        this.grosor = grosor;
        this.cantidad = cantidad;
        this.pies = pies;

    }
}
