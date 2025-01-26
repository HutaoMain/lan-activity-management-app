package com.alcantara.cafe_management_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;

    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;
}
