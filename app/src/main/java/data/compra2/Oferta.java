package data.compra2;

import java.io.Serializable;

public class Oferta implements Serializable{
    private String nombre;
    private String precio;
    private String descripcion;
    private String phone;

    public Oferta(String nombre, String precio, String descripcion, String phone) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.phone = phone;
    }


    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    //public String getDescripcion() {
       // return descripcion;
    //}

   // public String getPhone() {
        //return phone;
    //}
}