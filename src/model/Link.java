/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import javax.swing.ImageIcon;

/**
 *
 * @author Yuri
 */
public class Link {

    private int id;
    private String nome;
    private String url;
    private String grupo;
    private ImageIcon icon;
    private String iconName;
    private boolean iconLoading;
    private boolean isSelected;

    public Link(String nome, String url, String grupo) {
        this.iconName = null;
        this.nome = nome;
        this.url = url;
        this.grupo = grupo;
    }

    public Link(int id, String nome, String url, String grupo) {
        this.iconName = null;
        this.id = id;
        this.nome = nome;
        this.url = url;
        this.grupo = grupo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public boolean isIconLoading() {
        return iconLoading;
    }

    public void setIconLoading(boolean iconLoading) {
        this.iconLoading = iconLoading;
    }

    public boolean isSelected() {
        return isSelected;
    }
    

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        System.out.println("HHHHHHHHHHASHHH CODE????????");
        hash = 67 * hash + Objects.hashCode(this.url);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Link other = (Link) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Link{" + "url=" + url + '}';
    }

}
