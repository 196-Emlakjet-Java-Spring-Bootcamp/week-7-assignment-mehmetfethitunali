package com.emlakjet.finalproject.advertisement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "advertisements")
@Getter
@Setter
@AllArgsConstructor
public class Advertisement {

    public Advertisement(){
        this.view = 0L;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String detailedMessage;

    @Column
    private Long price;

    @Column
    private Date updatedAt;

    @Column
    private Date createdAt;

    @Column
    private String status;

    @Column
    private Long view;

    @Column
    private Long userId;
}
