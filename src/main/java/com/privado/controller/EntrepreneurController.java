package com.privado.controller;


import com.privado.model.Candidate;
import com.privado.services.EntrepreneurServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8084")
@RestController
@RequestMapping("ef")
public class EntrepreneurController {

    @Autowired
    EntrepreneurServices entrepreneurServices;

    @PostMapping("/groups")
    public ResponseEntity<? extends Object> crate(@RequestBody Candidate[] candidates){
        return  entrepreneurServices.crateGroups(candidates);
    }
}
