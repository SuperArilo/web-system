package com.example.backgroundsystem.vo;

import com.example.backgroundsystem.entity.Dynamic;
import lombok.Data;

import java.util.List;

@Data
public class DynamicVO extends Dynamic {

    private String username;

    private String headAddress;

    private Integer clazz;

    private List<String> imageAddresses;

    private List<String> usersWhoLikedIt;
}
