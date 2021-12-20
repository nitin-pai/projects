package com.techtracer.util.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class RecycleStats {
    
    
    @Column(name="id")
    @Id
    private long pid;
    @Column(name="app_server")
    private String appServer;
    @Column(name="status")
    private String status;

}
