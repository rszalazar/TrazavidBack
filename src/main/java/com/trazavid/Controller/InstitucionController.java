package com.trazavid.Controller;

import com.trazavid.Entity.Institucion;
import com.trazavid.Entity.Salon;
import com.trazavid.Service.AlumnoService;
import com.trazavid.Service.InstitucionService;
import com.trazavid.Service.SalonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api/instituciones")
@CrossOrigin("*")
public class InstitucionController {
    @Resource
    private InstitucionService institucionService;
    @Resource
    private SalonService salonService;

    //create a new line
    @PostMapping(value="/new")
    public ResponseEntity<?> create (@Valid @RequestBody Institucion institucion){
        Salon[] array= institucion.getSalone().toArray(new Salon[0]);
        //recorremos hasta el limite del array
        int i;
        for (i=0; i<array.length; i++) {//inicia el bucle
            salonService.save(array[i]);
            System.out.println(array[i]);
        }//cierra el bucle
        return ResponseEntity.status(HttpStatus.CREATED).body(institucionService.save(institucion));
    }

    @GetMapping(value = "/all")
    public Iterable<Institucion> getAll() {
        return institucionService.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read (@PathVariable(value = "id") Long institucionId){
        Optional<Institucion> oInstitucion= institucionService.findById(institucionId);
        if(!oInstitucion.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oInstitucion);
    }

    @GetMapping("/personal/{id}")
    public ResponseEntity<?> readInstitucion (@PathVariable(value = "id") Long institucionId){
        Optional<Institucion> oInstitucion= institucionService.findByIdPersonal(institucionId);
        if(!oInstitucion.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oInstitucion);
    }

    // Delete an User
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable(value= "id") Long institucionId) {
        if(!institucionService.findById(institucionId).isPresent()){
            return ResponseEntity.notFound().build();
        }
        institucionService.deleteById(institucionId);
        return ResponseEntity.ok().build();
    }


}
