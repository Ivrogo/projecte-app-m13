package com.movies4rent.Servidor.Service;


import com.movies4rent.Servidor.DTO.*;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.UsuariPagingRepository;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import com.movies4rent.Servidor.Utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe que executa els metodes cridats dins de la classe controller.
 * @author Ivan Rodriguez Gomez
 */
@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private UsuariPagingRepository usuariPagingRepository;
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Classe que llista tots els usuaris registrats en la base de dades paginats i amb la possibilitat de filtrarlos.
     * @param page numero de la pagina
     * @param pageSize quantitat de elements per pagina
     * @param nombre variable nombre que filtra la llista segons el nom
     * @param apellidos variable apellidos que filtra la llista segons els cognoms
     * @param username variable username que filtra la llista segons el nom d'usuari.
     * @param orden variable que serveix per organitzar la llista.
     * @param token token de sessió
     * @return un DTO amb el value de l'objecte d'una llista d'usuaris
     */
    @Override
    public ResponseEntity<ResponseDTO> findUsuariFiltered(int page, int pageSize, String nombre, String apellidos, String username, String orden, String token) {

        ResponseDTO<Page<Usuari>> response = new ResponseDTO();
        PageRequest pr = PageRequest.of(page, pageSize);

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesion no valida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (tokenUtils.isUserAdmin(token) == false) {
            response.setMessage("No tienes permisos para realizar esta accion");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            List<Usuari> foundUsuaris = usuariRepository.findAll();
            Page<Usuari> usuariosSinFiltroNiOrden = new PageImpl<>(foundUsuaris, pr, foundUsuaris.size());
            UserFilterDTO filter = new UserFilterDTO(nombre, apellidos, username);

            if (foundUsuaris.isEmpty()){
                response.setMessage("No hay usuarios");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (orden == null || orden.isEmpty()) {
                if (nombre == null && apellidos == null && username == null) {
                    response.setMessage("Mostrando usuarios...");
                    response.setValue(usuariosSinFiltroNiOrden);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                List<Usuari> usuarisFiltradosSinOrdenar = foundUsuaris.stream().filter(filter.getPredicate()).collect(Collectors.toList());
                Page<Usuari> usuarisFiltradosUnsorted = new PageImpl<>(usuarisFiltradosSinOrdenar, pr, usuarisFiltradosSinOrdenar.size());

                response.setMessage("Mostrando usuarios filtrados y sin ordenar...");
                response.setValue(usuarisFiltradosUnsorted);
                return new ResponseEntity<>(response, HttpStatus.OK);

            }else{

                Comparator<Usuari> comparator = null;
                switch (orden) {
                    case "nombreAsc":
                        comparator = Comparator.comparing(Usuari::getNombre);
                        break;
                    case "nombreDesc":
                        comparator = Comparator.comparing(Usuari::getNombre).reversed();
                        break;
                    case "apellidosAsc":
                        comparator = Comparator.comparing(Usuari::getApellidos);
                        break;
                    case "apellidosDesc":
                        comparator = Comparator.comparing(Usuari::getApellidos).reversed();
                        break;
                    case "usernameAsc":
                        comparator = Comparator.comparing(Usuari::getUsername);
                        break;
                    case "usernameDesc":
                        comparator = Comparator.comparing(Usuari::getUsername).reversed();
                        break;
                    default:
                        break;
                }
                List<Usuari> usuarisFinal = foundUsuaris.stream().filter(filter.getPredicate()).sorted(comparator).collect(Collectors.toList());
                Page<Usuari> usuaris = new PageImpl<>(usuarisFinal, pr, usuarisFinal.size());

                response.setMessage("Mostrando usuarios filtrados y ordenados...");
                response.setValue(usuaris);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setMessage("Error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Classe que troba un usuari especific en la base de dades.
     * @param id id de l'usuari
     * @param token token de sessió
     * @return DTO amb el value de l'objecte que conté la informació de l'usuari
     */
    @Override
    public ResponseEntity<ResponseDTO> findById(UUID id, String token) {
        ResponseDTO<GetUsuariDTO> response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> user = tokenUtils.getUser(token);
            if (user == null || !user.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<Usuari> foundUsuari = usuariRepository.findById(id);
            if (!foundUsuari.isPresent()) {
                response.setMessage("Usuario no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.setMessage("Mostrando la información del usuario");
            response.setValue(GetUsuariDTO.fromEntityToDTO(foundUsuari.get()));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que actualitza un usuari en la base de dades.
     * @param userUpdateDTO DTO amb la informació canviada de l'usuari
     * @param token token de sessió
     * @return DTO amb un missatge d'actualització
     */
    @Override
    public ResponseEntity<ResponseDTO> updateUser(UserUpdateDTO userUpdateDTO, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> user = tokenUtils.getUser(token);
            if (user == null || !user.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            UserUpdateDTO.updateEntityFromDTO(user.get(), userUpdateDTO);
            usuariRepository.save(user.get());

            response.setValue(user.get());
            response.setMessage("Usuario actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que cambia el rol d'un usuari en la base de dades.
     * @param admin boolean que indica si el usuari es administrador o no
     * @param id id de l'usuari
     * @param token token de sessió
     * @return DTO amb un missatge d'actualització
     */
    @Override
    public ResponseEntity<ResponseDTO> updateUserAdmin(boolean admin, UUID id, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (tokenUtils.isUserAdmin(token) == false) {
            response.setMessage("No tienes permisos para realizar esta accion");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Optional<Usuari> updatedUsuari = usuariRepository.findById(id);
            if (!updatedUsuari.isPresent()) {
                response.setMessage("Usuari no trobat");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            updatedUsuari.get().setIsAdmin(admin);
            usuariRepository.save(updatedUsuari.get());

            response.setValue(updatedUsuari.get());
            response.setMessage("Usuario actualizado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error Excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que elimina un usuari en la base de dades.
     * @param id id de l'usuari
     * @param token token de sessió
     * @return DTO amb un missatge de confirmació d'usuari esborrat
     */
    @Override
    public ResponseEntity<ResponseDTO> deleteUser(UUID id, String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> updatedUsuari = usuariRepository.findById(id);
            if (!updatedUsuari.isPresent()) {
                response.setMessage("Usuari no trobat");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            usuariRepository.deleteById(id);
            response.setMessage("Usuario eliminado");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error Excepció trobada");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Classe que obté l'usuari al qual està assignat el token de sessió.
     * @param token token de sessió
     * @return DTO amb el value de l'objecte que conté la informació de l'usuari
     */
    @Override
    public ResponseEntity<ResponseDTO> getUserByToken(String token) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> user = tokenUtils.getUser(token);
            if (user == null || !user.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.setValue(UserInfoDTO.fromEntityToDTO(user.get()));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> changeUsuariPassword(String token, UserChangePasswordDTO userChangePasswordDTO) {
        ResponseDTO response = new ResponseDTO();

        if (!tokenUtils.isTokenValid(token)) {
            response.setMessage("Sesión no válida");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            Optional<Usuari> foundUser = tokenUtils.getUser(token);
            if (foundUser == null ||!foundUser.isPresent()) {
                response.setMessage("Sesión no válida");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            UserChangePasswordDTO.fromDTOToEntityUpdate(foundUser.get(), userChangePasswordDTO);
            usuariRepository.save(foundUser.get());
            response.setValue(foundUser.get());
            response.setMessage("Contraseña actualitzada");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setMessage("Error.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
