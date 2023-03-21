package iscream.webshop.controller;

import iscream.webshop.DAO.IcecreamDAO;
import iscream.webshop.model.Icecream;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/icecream")
public class IcecreamController {

    private final IcecreamDAO icecreamDAO;

    public IcecreamController(IcecreamDAO icecreamDAO) {
        this.icecreamDAO = icecreamDAO;
    }

    @GetMapping
    public ResponseEntity<List<Icecream>> getAllIcecream() {
        return ResponseEntity.ok(
                this.icecreamDAO.getAllIcecream()
        );
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Icecream> getIcecreamById(@PathVariable Long id) {
        try {
            Icecream checkIcecream = this.icecreamDAO.getIcecreamById(id);
            return new ResponseEntity<>(checkIcecream, HttpStatus.FOUND);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Icecream> addIcecream(@RequestBody Icecream icecream) {
        if (this.icecreamDAO.addIcecream(icecream) == null) {
            throw new NullPointerException("Icecream is empty!");
        } else {
            return ResponseEntity.ok(icecream);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Icecream> updateIcecream(@PathVariable Long id, Icecream icecream) throws ChangeSetPersister.NotFoundException {
        try {
            icecreamDAO.getIcecreamById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(
                icecreamDAO.updateIcecream(id, icecream)
        );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteWasteData(@PathVariable final Long id) {
        try {
            icecreamDAO.deleteIcecreamById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
}
