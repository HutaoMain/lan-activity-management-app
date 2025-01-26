package com.alcantara.cafe_management_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
public class ComputerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String hostName;
    @OneToMany(mappedBy = "computerInfo")
    private List<Message> messages;
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;
    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;
}
