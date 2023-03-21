//package com.burat.simpel.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "training_taken_user")
//@AllArgsConstructor
//@NoArgsConstructor
//public class TrainingTakenUser {
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long idTrainingTaken;
////
////    @ManyToOne(fetch = FetchType.EAGER, optional = false)
////    @JoinColumn(name = "user_uuid", referencedColumnName = "account_uuid", nullable = false)
////    @OnDelete(action = OnDeleteAction.CASCADE)
////    private UserModel userModel;
////
////    @ManyToOne(fetch = FetchType.EAGER, optional = false)
////    @JoinColumn(name = "training_uuid", referencedColumnName = "training_uuid", nullable = false)
////    @OnDelete(action = OnDeleteAction.CASCADE)
////    private TrainingModel trainingModel;
////
////    @NotNull
////    @Column(name = "training_start", nullable = true)
////    @DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm")
////    private LocalDateTime trainingStart;
////
////    @NotNull
////    @Column(name = "training_done", nullable = true)
////    @DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm")
////    private LocalDateTime trainingDone;
//}
