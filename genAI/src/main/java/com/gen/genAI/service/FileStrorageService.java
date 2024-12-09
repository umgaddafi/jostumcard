package com.gen.genAI.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileStrorageService {
    // private static final String DIRECTORY="C:\\Users\\Public\\Desktop";
    private static final String DIRECTORY="C:\\Users\\ID Card\\ServerFiles";
    public List<String> getAllFiles(){
        
        File file=new File(DIRECTORY);
        if(!file.isDirectory()){
           throw new NullPointerException("The name given is not a folder");
        }
        List<String> list=new ArrayList<>();
        for(File f: file.listFiles()){
            list.add(f.getName());
        }
        return list;
    }
    public File getFile(String file)throws Exception{
        if(file==null)throw new FileNotFoundException("File is not found");
        File newFile=new File(DIRECTORY+File.separator+file);
        if(!newFile.exists())throw new FileNotFoundException("file not found");
        return newFile;

    }
    public void storeFile(MultipartFile file)throws Exception{
        if(file==null) throw new NullPointerException("Invalid Request");
        File storeFile=new File(DIRECTORY+File.separator+file.getOriginalFilename());
        if(!storeFile.getParent().equals(DIRECTORY))throw new NullPointerException("Wrong Path");
        Files.copy(file.getInputStream(),storeFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
    }
    
}
