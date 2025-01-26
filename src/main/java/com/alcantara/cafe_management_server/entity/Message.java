package com.alcantara.cafe_management_server.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "computer_info_id")
    @NotNull(message = "ComputerInfo cannot be null")
    private ComputerInfo computerInfo;

    @NotNull(message = "Content cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    private String content;

    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdOn;

    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime lastUpdatedOn;
}
