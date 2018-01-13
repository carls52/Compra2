package data.compra2;

import java.io.Serializable;

/**
 * Created by Carlos Gil Sabrido on 13/01/2018.
 */

public class Usuario implements Serializable{
    private String correo;
    private String pass;

    public Usuario(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
