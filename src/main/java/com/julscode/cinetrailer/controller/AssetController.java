package com.julscode.cinetrailer.controller;

import com.julscode.cinetrailer.service.FileSystemStoregeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    private FileSystemStoregeService fileSystemStoregeService;

    @GetMapping("/{filename:.+}")
    Resource getResource(@PathVariable("filename") String filename){

        return fileSystemStoregeService.loadAsResource(filename);

    }
}
