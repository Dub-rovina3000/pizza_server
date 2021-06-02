package com.project.services;

import com.project.entity.State;
import com.project.repositories.StateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    @Autowired
    private StateRepo stateRepo;

    public State create(State state){
        return stateRepo.save(state);
    }

    public List<State> findAll(){
        return stateRepo.findAll();
    }

    public Optional<State> findById(Long id){
        return stateRepo.findById(id);
    }

    public boolean change(State state, Long id){
        if (findById(id).isPresent()) {
            state.setId(id);
            stateRepo.save(state);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        if (findById(id).isPresent()) {
            stateRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
