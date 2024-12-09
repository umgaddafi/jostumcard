package com.gen.genAI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.gen.genAI.service.FileStrorageService;


@RestController
@RequestMapping("/umar")
public class Controller {
@Autowired
   private FileStrorageService fileStrorageService;

   @GetMapping("/download")
   public ResponseEntity<FileSystemResource> downloadFile(@RequestParam("filename") String filename){

    if(filename==null) throw new NullPointerException("File Name cannot be empty");
    try {
        File file=fileStrorageService.getFile(filename);
        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "Attachment; filename=\""+filename+"\"")
        .contentLength(file.length())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(new FileSystemResource(file));
    } catch (Exception e) {
        throw new NullPointerException(e.getMessage());
    }

   }
   @PostMapping("/upload")
   public ResponseEntity<Map<String,String>> uploadFile(@RequestParam("file") MultipartFile file){
        Map<String, String> mess=new HashMap<>();
        Map<String, String> error=new HashMap<>(); 
        error.put("error","Sorry the server cannot process the file");
        mess.put("Success","Dr. Mohammad Maher your file has been stored successfully");
     if(file==null) return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
     try {
        fileStrorageService.storeFile(file);
        
     } catch (Exception e) {
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        
     }
     return new ResponseEntity<>(mess,HttpStatus.ACCEPTED);
   }
}
