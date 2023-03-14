package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;

import java.util.UUID;

public class GetUsuariDTO {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String dirección;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDirección(String dirección) {
        this.dirección = dirección;
    }


    public static void fromDTOToEntity(GetUsuariDTO getUsuariDTO, Usuari usuari) {

        usuari.setNombre(getUsuariDTO.getNombre());
        usuari.setApellidos(getUsuariDTO.getApellidos());
        usuari.setEmail(getUsuariDTO.getEmail());
        usuari.setDireccion(getUsuariDTO.getDirección());
        usuari.setTelefono(getUsuariDTO.getTelefono());
    }

    public static GetUsuariDTO fromEntityToDTO(Usuari usuari) {
        GetUsuariDTO getUsuariDTO = new GetUsuariDTO();

        getUsuariDTO.setId(usuari.getId());
        getUsuariDTO.setNombre(usuari.getNombre());
        getUsuariDTO.setApellidos(usuari.getApellidos());
        getUsuariDTO.setEmail(usuari.getEmail());
        getUsuariDTO.setDirección(usuari.getDireccion());
        getUsuariDTO.setTelefono(usuari.getTelefono());

        return getUsuariDTO;
    }
}
