package com.gen.genAI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gen.genAI.service.FileStrorageService;


@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping("/")
public class FileController {

    @Autowired
    private FileStrorageService fileStorageService;

 
    @GetMapping("/files")
    public String listFiles(Model model) {
        
        List<String> files = fileStorageService.getAllFiles();
        model.addAttribute("files", files);
        return "index";
    }

  
    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam("filename") String filename) {
        if (filename == null) throw new NullPointerException("File Name cannot be empty");
        try {
            File file = fileStorageService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "Attachment; filename=\"" + filename + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(file));
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
    @PostMapping("/upload")
public RedirectView uploadFile(@RequestParam("file") MultipartFile file) {
    Map<String, String> mess = new HashMap<>();
    Map<String, String> error = new HashMap<>();
    error.put("error", "Sorry, the server cannot process the file.");
    mess.put("success", "Dr. Mohammad Maher, your file has been stored successfully.");

    if (file == null || file.isEmpty()) {
        // Redirect with error message
        return new RedirectView("/umar/resources?message=" + error.get("error") + "&isSuccess=false");
    }

    try {
        // Your file storage service logic
        fileStorageService.storeFile(file);

        // Redirect with success message
        return new RedirectView("/umar/resources?message=" + mess.get("success") + "&isSuccess=true");
    } catch (Exception e) {
        // Redirect with error message if something goes wrong
        return new RedirectView("/umar/resources?message=" + error.get("error") + "&isSuccess=false");
    }
}

    
    @GetMapping("/*")
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        return model;
    }
    
}
