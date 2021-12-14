package modelos;

public class Comprarmaderaa {
     double cantidad=0;
    double ancho=0;
    double largo=0;
    double precio=0;
    double total=0;
    String provedor="";

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) {
        this.provedor = provedor;
    }

    public Comprarmaderaa(double cantidad, double ancho, double largo, double precio, double total, String proveedor) {
        this.cantidad = cantidad;
        this.ancho = ancho;
        this.largo = largo;
        this.precio = precio;
        this.total=total;
        this.provedor=proveedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getAncho() {
        return ancho;
    }

    public double getLargo() {
        return largo;
    }

    public double getPrecio() {
        return precio;
    }
}
