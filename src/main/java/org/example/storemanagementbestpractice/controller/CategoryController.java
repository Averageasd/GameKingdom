package org.example.storemanagementbestpractice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CategoryController {

    @GetMapping(path = "category")
    public ResponseEntity<Void> getCategories() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "privateCategory")
    public ResponseEntity<Void> getPrivateCategories() {
        log.info("private categories not allowed");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
