package com.example.challengegrupowl.controller;

import com.example.challengegrupowl.model.users;
import com.example.challengegrupowl.util.connectionDB;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/colaboradores")
public class testControllerRest {

    private final Connection connection;
    private final users user;
    private final List<users> getUsers = new ArrayList<>();

    testControllerRest() {
        connection = connectionDB.getConnection();
        user = new users();
    }

    @GetMapping
    public String menu() {
        return "Para inserir = https://wlgroupchallenge.herokuapp.com/colaboradores/insert/cpf/nome/item" +
                "\nPara atualizar = https://wlgroupchallenge.herokuapp.com/colaboradores/update/nome/item/cpf" +
                "\nPara excluir = https://wlgroupchallenge.herokuapp.com/colaboradores/delete/cpf" +
                "\nPara visualizar = https://wlgroupchallenge.herokuapp.com/colaboradores/listall";
    }

    @GetMapping("/insert/{cpf}/{name}/{item}")
    public String insertUsers(@PathVariable Long cpf, @PathVariable String name, @PathVariable String item) throws SQLException {
        if(cpf == null || name == null)
            return "Requer cpf e nome válido";
        if(getUsers.stream().anyMatch(t -> t.getCpf() == cpf) || cpf.toString().length() != 11)
            return "Cpf já cadastrado ou cpf invalido";
        if(getUsers.stream().anyMatch(t -> t.getItem().equals(item)))
            return "Item já cadastrado";

        user.setCpf(cpf);
        user.setName(name);
        user.setItem(item);

        PreparedStatement stmt = connection
                .prepareStatement("INSERT INTO usuarios (cpf, name, item) VALUES (?, ?, ?)");

        stmt.setLong(1, user.getCpf());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getItem());
        stmt.executeUpdate();

        return "Inserido no banco!\n Visualizar todos https://peopleapidigitalinnovationone.herokuapp.com/colaboradores/listall";
    }

    @GetMapping("/delete/{cpf}")
    public String deleteUsers(@PathVariable Long cpf) throws SQLException {
        if(getUsers.stream().noneMatch(t -> t.getCpf() == cpf))
            return "Nenhum cpf encontrado";

        user.setCpf(cpf);

        PreparedStatement stmt = connection
                .prepareStatement("DELETE FROM usuarios WHERE cpf = ?");

        stmt.setLong(1, cpf);
        stmt.executeUpdate();

        return "Deletado do banco!\n Visualizar todos https://peopleapidigitalinnovationone.herokuapp.com/colaboradores/listall";
    }

    @GetMapping("/update/{item}/{name}/{cpf}")
    public String updateUser(@PathVariable long cpf, @PathVariable String name, @PathVariable String item) throws SQLException {
        if(getUsers.stream().noneMatch(t -> t.getCpf() == cpf))
            return "Nenhum cpf encontrado";

        user.setCpf(cpf);
        user.setName(name);
        user.setItem(item);

        PreparedStatement stmt = connection
                .prepareStatement("UPDATE usuarios SET name = ?, item = ? WHERE cpf = ?");

        stmt.setString(1, user.getName());
        stmt.setString(2, user.getItem());
        stmt.setLong(3, user.getCpf());
        stmt.executeUpdate();

        return "Atualizado no banco!\n Visualizar todos https://peopleapidigitalinnovationone.herokuapp.com/colaboradores/listall";
    }

    @GetMapping("/listall")
    public List<users> getAllUsers() throws SQLException {

        getUsers.clear();

        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM usuarios");

        while(resultSet.next())
            getUsers.add(new users
                    (resultSet.getLong("cpf"),resultSet.getString("name"), resultSet.getString("item")));

        return getUsers;
    }
}
