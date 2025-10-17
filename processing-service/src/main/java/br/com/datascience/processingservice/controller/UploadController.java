package br.com.datascience.processingservice.controller;

import br.com.datascience.processingservice.entity.Dataset;
import br.com.datascience.processingservice.entity.User;
import br.com.datascience.processingservice.repository.UserRepository;
import br.com.datascience.processingservice.service.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserRepository userRepository;

    // Endpoint para receber o CSV
    @PostMapping("/csv")
    public Dataset uploadCsv(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        // Busca o usuário pelo ID no banco
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        // Chama o serviço para processar o CSV
        return uploadService.processaUploadCsvVendas(file, user);
    }
}
