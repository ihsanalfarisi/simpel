package com.burat.simpel.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="user")
//@DiscriminatorValue("User")
public class UserModel extends AccountModel{
//    @OneToMany(mappedBy = "userModel", fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    List<TrainingTakenUser> listTrainingTakenByUser;
}
