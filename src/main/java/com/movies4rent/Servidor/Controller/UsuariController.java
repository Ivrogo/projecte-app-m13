package com.movies4rent.Servidor.Controller;



import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Repository.UsuariRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Er_jo
 */
@RestController
public class UsuariController {
    
    @Autowired
    private UsuariRepository repository;
    
    @GetMapping(value ="/demo")
    public String demo() {
        return "hola mundo";
    }
 
    @GetMapping(value = "/usuaris")
    public List<Usuari> getUsuaris() {
        return repository.findAll();
    }
    
    @PostMapping(value = "/addusuari")
    public String addUsuari(@RequestBody Usuari usuari) {
        repository.save(usuari);
        return "Usuari afegit";
    }
    
    @PutMapping(value="/updateusuari/{id}")
    public String updateUsuari(@PathVariable long id, @RequestBody Usuari usuari){
        
        Usuari updateUsuari = repository.findById(id).get();
        updateUsuari.setNombre(usuari.getNombre());
        updateUsuari.setApellidos(usuari.getApellidos());
        updateUsuari.setEmail(usuari.getEmail());
        updateUsuari.setAddress(usuari.getAddress());
        updateUsuari.setTelefono(usuari.getTelefono());
        updateUsuari.setUsername(usuari.getUsername());
        updateUsuari.setPassword(usuari.getPassword());
        updateUsuari.setConfirmPassword(usuari.getConfirmPassword());
        
        repository.save(updateUsuari);
        
        return "Usuari actualitzat correctament";
    }
    
    @DeleteMapping(value = "/deleteusuari/{id}")
    public String deleteUsuari(@PathVariable long id) {
        Usuari deletedUsuari = repository.findById(id).get();
        repository.delete(deletedUsuari);
        return "Usuari esborrat";
    }
}
