package com.julscode.cinetrailer.service;

import com.julscode.cinetrailer.exception.FileNotFoundException;
import com.julscode.cinetrailer.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStoregeService implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    @PostConstruct
    @Override
    public void init(){
        try {
            Files.createDirectories(Paths.get(storageLocation));

        }catch (IOException e){
            throw  new StorageException("Error al inciar la ubicacion del almacen de archivos");
        }
    }


    @Override
    public String storage(MultipartFile file) {

        String filename = file.getOriginalFilename();

        if(file.isEmpty()){
            throw  new StorageException("No se puede almacenar un archivo vacio");
        }
        try{
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,Paths.get(storageLocation).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new StorageException("Error al almacenar el archivo: "+ filename);
        }

        return filename;
    }

    @Override
    public Path load(String filename) {

        return Paths.get(storageLocation).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {

        try{
            Path file = load(filename);
            Resource resource =  new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return  resource;
            }else{
                throw  new FileNotFoundException("NO se pudo encontrar el archivo: "+ filename);
            }
        }catch (MalformedURLException e){
            throw new FileNotFoundException("NO se pudo encontrar el archivo: " +filename,e);
        }
    }

    @Override
    public void delete(String filename) {

        Path file = load(filename);
        try {
            FileSystemUtils.deleteRecursively(file);

        }catch (IOException e){

        }

    }
}
