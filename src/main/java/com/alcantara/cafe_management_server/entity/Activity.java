package com.alcantara.cafe_management_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String activeWindow;
    private LocalDateTime activeDateTime;
    @ManyToOne
    @JoinColumn(name = "computer_info_id")
    private ComputerInfo computerInfo;
}
