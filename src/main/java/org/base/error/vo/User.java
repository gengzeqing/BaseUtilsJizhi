package org.base.error.vo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}