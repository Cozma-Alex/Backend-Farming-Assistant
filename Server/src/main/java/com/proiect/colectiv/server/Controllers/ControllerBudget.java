package com.proiect.colectiv.server.Controllers;

import com.proiect.colectiv.server.Persistence.RepositoryBudget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ControllerBudget {

    @Autowired
    private RepositoryBudget repositoryBudget;
}
