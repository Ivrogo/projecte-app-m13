package com.movies4rent.Servidor.DTO;

import com.movies4rent.Servidor.Entities.Usuari;

import java.util.UUID;

/**
 * Classe DTO per obtenir un usuari
 * @author Iván Rodríguez Gómez
 */
public class GetUsuariDTO {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String direccion;

    private boolean isIsAdmin;

    /**
     * Getters i Setters
     */
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isIsAdmin() {
        return isIsAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        isIsAdmin = isAdmin;
    }

    /**
     * Transforma el DTO a un objecte usuari
     * @param getUsuariDTO DTO amb les dades de l'usuari a transformar
     * @param usuari objecte usuari
     */
    public static void fromDTOToEntity(GetUsuariDTO getUsuariDTO, Usuari usuari) {

        usuari.setNombre(getUsuariDTO.getNombre());
        usuari.setApellidos(getUsuariDTO.getApellidos());
        usuari.setEmail(getUsuariDTO.getEmail());
        usuari.setDireccion(getUsuariDTO.getDireccion());
        usuari.setTelefono(getUsuariDTO.getTelefono());
        usuari.setAdmin(getUsuariDTO.isIsAdmin());
    }

    /**
     * Transforma un objecte usuari a un DTO
     * @param usuari Objecte usuari de la base de dades
     * @return un DTO amb la informació extraida de l'objecte usuari
     */
    public static GetUsuariDTO fromEntityToDTO(Usuari usuari) {
        GetUsuariDTO getUsuariDTO = new GetUsuariDTO();

        getUsuariDTO.setId(usuari.getId());
        getUsuariDTO.setNombre(usuari.getNombre());
        getUsuariDTO.setApellidos(usuari.getApellidos());
        getUsuariDTO.setEmail(usuari.getEmail());
        getUsuariDTO.setDireccion(usuari.getDireccion());
        getUsuariDTO.setTelefono(usuari.getTelefono());
        getUsuariDTO.setIsAdmin(usuari.isIsAdmin());

        return getUsuariDTO;
    }
}
