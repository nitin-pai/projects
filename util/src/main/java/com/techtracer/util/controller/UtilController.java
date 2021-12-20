package com.techtracer.util.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import com.techtracer.util.data.RecycleStats;
import com.techtracer.util.data.RecycleStatsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {

    @Autowired
    RecycleStatsRepository repo;
    
    @GetMapping("/health")
    public Status health() {
        Status status = new Status();
        status.setStatus("success");
        return status;
    }

    @GetMapping("/stats")
    public List<RecycleStats> getRecycleStats() {
        List<RecycleStats> list = new ArrayList<>();
        repo.findAll().forEach(e -> list.add(e));
        return list;
    }

    @GetMapping("/stats/{server}")
    public RecycleStats getRecycleStats(@PathVariable String server) {
        return repo.findByAppServer(server);
    }

}
