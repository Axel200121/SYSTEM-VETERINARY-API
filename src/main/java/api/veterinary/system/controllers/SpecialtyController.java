package api.veterinary.system.controllers;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.SpecialtyDTO;
import api.veterinary.system.services.SpecialtyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/veterinary/specialty")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO> saveSpecialty(@Valid @RequestBody SpecialtyDTO specialtyDTO, BindingResult bindingResult){
        ApiResponseDTO response = specialtyService.saveSpecialty(specialtyDTO,bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> updateSpecialty(@PathVariable String id, @Valid @RequestBody SpecialtyDTO specialtyDTO, BindingResult bindingResult){
        ApiResponseDTO response = specialtyService.updateSpecialty(id,specialtyDTO,bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO> getSpecialty(@PathVariable String id){
        ApiResponseDTO response = specialtyService.getSpecialty(id);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseDTO> getAllSpecialties(){
        ApiResponseDTO response = specialtyService.getAllSpecialty();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO> deleteSpecialty(@PathVariable String id){
        ApiResponseDTO response = specialtyService.deleteSpecialty(id);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

}
