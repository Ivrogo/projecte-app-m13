package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;
import org.springframework.data.annotation.Transient;

/**
 * Clase DTO para cambiar la contraseña de un usuario
 * @author Ivan Rodriguez Gomez
 */
public class UserChangePasswordDTO {

    private String password;

    @Transient
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public static void fromDTOToEntityUpdate(Usuari usuari, UserChangePasswordDTO userPasswordDTO){

        usuari.setPassword(userPasswordDTO.getPassword());

    }
}
