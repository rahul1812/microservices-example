package com.lcwd.UserPortal.UserPortal.entites;




import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name= "micro_users")
public class User {

    @Id
    private String email;
    private String userID;
    private String name ;
    private String about;
    private String password;

    @Transient
    private List<Rating> ratings = new ArrayList<>();


    //Add more fields that you want related to user
}
