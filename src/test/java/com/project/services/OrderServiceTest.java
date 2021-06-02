package com.project.services;

import com.project.entity.Ingredient;
import com.project.entity.Order;
import com.project.repositories.OrderRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepo orderRepo;

    @Test
    public void create() {
        Order order = new Order();
        order.setId(1L);
        order.setPizzaAmount("");

        Mockito.doReturn(order).when(orderRepo).save(order);
        Assert.assertEquals(order.getId(), orderService.create(order));
    }

    @Test
    public void findAll() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());

        Mockito.doReturn(orderList).when(orderRepo).findAll();
        Assert.assertEquals(orderList, orderService.findAll());
    }

    @Test
    public void findByClientId() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        Long id = 1L;

        Mockito.doReturn(orderList).when(orderRepo).findByClientId(id, pageable);
        Assert.assertEquals(orderList, orderService.findByClientId(id));
    }

    @Test
    public void findById() {
        Order order = new Order();
        Optional optionalOrder = Optional.of(order);
        Long id = 1L;

        Mockito.doReturn(optionalOrder).when(orderRepo).findById(id);
        Assert.assertEquals(optionalOrder, orderService.findById(id));
    }

    @Test
    public void change() {
        Order order = new Order();
        Long id = 1L;
        order.setId(id);
        Optional optionalOrder = Optional.of(order);

        Mockito.doReturn(optionalOrder).when(orderRepo).findById(id);
        Assert.assertTrue(orderService.change(order, order.getId()));
    }

    @Test
    public void delete() {
        Order order = new Order();
        Long id = 1L;
        order.setId(id);
        Optional optionalOrder = Optional.of(order);

        Mockito.doReturn(optionalOrder).when(orderRepo).findById(id);
        Assert.assertTrue(orderService.delete(order.getId()));
    }

    @Test
    public void findOrderWithoutCook() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());

        Mockito.doReturn(orderList).when(orderRepo).findAllOrdersWithoutCook(pageable);
        Assert.assertEquals(orderList, orderService.findOrderWithoutCook());
    }

    @Test
    public void findOrderWithoutCourier() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());

        Mockito.doReturn(orderList).when(orderRepo).findAllOrdersWithoutCourier(pageable);
        Assert.assertEquals(orderList, orderService.findOrderWithoutCourier());
    }

    @Test
    public void findByCook() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        Long id = 1L;

        Mockito.doReturn(orderList).when(orderRepo).findByCookId(id, pageable);
        Assert.assertEquals(orderList, orderService.findByCook(id));
    }

    @Test
    public void findByCourier() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        Long id = 1L;

        Mockito.doReturn(orderList).when(orderRepo).findByCourier(id, pageable);
        Assert.assertEquals(orderList, orderService.findByCourier(id));
    }

    @Test
    public void findWorkByCook() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        Long id = 1L;

        Mockito.doReturn(orderList).when(orderRepo).findWorkByCookId(id, pageable);
        Assert.assertEquals(orderList, orderService.findWorkByCook(id));
    }

    @Test
    public void findWorkByCourier() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        Long id = 1L;

        Mockito.doReturn(orderList).when(orderRepo).findWorkByCourier(id, pageable);
        Assert.assertEquals(orderList, orderService.findWorkByCourier(id));
    }
}