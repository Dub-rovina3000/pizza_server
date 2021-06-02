package com.project.services;

import com.project.entity.State;
import com.project.repositories.StateRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateServiceTest {

    @Autowired
    private StateService stateService;

    @MockBean
    private StateRepo stateRepo;

    @Test
    public void create() {
        State state = new State();
        state.setName("Сыр");

        Mockito.doReturn(state).when(stateRepo).save(state);
        Assert.assertEquals(state, stateService.create(state));
    }

    @Test
    public void findAll() {
        List<State> stateList = new ArrayList<>();
        stateList.add(new State());

        Mockito.doReturn(stateList).when(stateRepo).findAll();
        Assert.assertEquals(stateList, stateService.findAll());
    }

    @Test
    public void findById() {
        State state = new State();
        Optional optionalState = Optional.of(state);
        Long id = 1L;

        Mockito.doReturn(optionalState).when(stateRepo).findById(id);
        Assert.assertEquals(optionalState, stateService.findById(id));
    }

    @Test
    public void change() {
        State state = new State();
        Long id = 1L;
        state.setId(id);
        Optional optionalState = Optional.of(state);

        Mockito.doReturn(optionalState).when(stateRepo).findById(id);
        Assert.assertTrue(stateService.change(state, state.getId()));
    }

    @Test
    public void delete() {
        State state = new State();
        Long id = 1L;
        state.setId(id);
        Optional optionalState = Optional.of(state);

        Mockito.doReturn(optionalState).when(stateRepo).findById(id);
        Assert.assertTrue(stateService.delete(state.getId()));
    }
}