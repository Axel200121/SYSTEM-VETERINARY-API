package api.veterinary.system.controllers;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.PermissionDTO;
import api.veterinary.system.services.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/veterinary/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO> savePermission(@Valid @RequestBody PermissionDTO permissionDTO, BindingResult bindingResult){
        ApiResponseDTO response = this.permissionService.savePermission(permissionDTO, bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> updatePermission(@PathVariable String id, @Valid@RequestBody PermissionDTO permissionDTO, BindingResult bindingResult){
        ApiResponseDTO response = this.permissionService.updatePermission(id, permissionDTO, bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO> getPermission(@PathVariable String id){
        ApiResponseDTO response = this.permissionService.getPermission(id);
        return  new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseDTO> getAllPermission(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page,size);
        ApiResponseDTO response = permissionService.getAllPermission(pageable);
        return  new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponseDTO> deletePermission(@PathVariable String id){
        ApiResponseDTO response = this.permissionService.deletePermission(id);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }



}
