package com.project.controller;

import com.project.entity.State;
import com.project.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://pizzasite")
@RestController
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/states")
    public void create(@RequestBody State state){
        stateService.create(state);
    }

    @GetMapping("/states")
    public ResponseEntity<List<State>> findAll(){
        final List<State> stateList = stateService.findAll();

        if (stateList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(stateList, HttpStatus.OK);
    }

    @GetMapping("/states/{id}")
    public ResponseEntity<Optional<State>> findById(@PathVariable(name = "id") Long id ) {
        final Optional<State> state = stateService.findById(id);

        if (state.isPresent()) {
            return new ResponseEntity<>(state, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/states/{id}")
    public ResponseEntity<?> change(@RequestBody State state, @PathVariable(name = "id") Long id) {
        if (stateService.change(state, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/states/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (stateService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
