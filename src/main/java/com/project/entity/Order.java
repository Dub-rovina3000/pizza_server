package com.project.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jca.support.LocalConnectionFactoryBean;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderTime;
    private LocalDateTime finishTime;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @ManyToMany
    private Set<Pizza> pizzaSet;

    @Column(nullable = false)
    private String pizzaAmount;

    @ManyToOne
    @JoinColumn(name = "cook_id")
    private Employee cook;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Employee courier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Employee getCook() {
        return cook;
    }

    public void setCook(Employee cook) {
        this.cook = cook;
    }

    public Employee getCourier() {
        return courier;
    }

    public void setCourier(Employee courier) {
        this.courier = courier;
    }

    public Set<Pizza> getPizzaSet() {
        return pizzaSet;
    }

    public void setPizzaSet(Set<Pizza> pizzaSet) {
        this.pizzaSet = pizzaSet;
    }

    public String getPizzaAmount() {
        return pizzaAmount;
    }

    public void setPizzaAmount(String pizzaAmount) {
        this.pizzaAmount = pizzaAmount;
    }
}
