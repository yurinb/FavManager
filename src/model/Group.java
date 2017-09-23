/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author Yuri
 */
public class Group {

    private int id;
    private String nome;
    private boolean secret;

    public Group(int id, String nome, boolean secret) {
        this.id = id;
        this.nome = nome;
        this.secret = secret;
    }

    public Group(String nome, boolean secret) {
        this.nome = nome;
        this.secret = secret;
    }

    public boolean isSecret() {
        return secret;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Group{" + "nome=" + nome + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

}
