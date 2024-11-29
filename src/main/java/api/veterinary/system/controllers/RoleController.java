package api.veterinary.system.controllers;


import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.RoleDTO;
import api.veterinary.system.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/veterinary/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO> saveRole(@Valid @RequestBody RoleDTO roleDTO, BindingResult bindingResult){
        ApiResponseDTO response = roleService.saveRole(roleDTO, bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> updateRole(@PathVariable String id, @Valid@RequestBody RoleDTO roleDTO, BindingResult bindingResult){
        ApiResponseDTO response = roleService.updateRole(id, roleDTO, bindingResult);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO> getRole(@PathVariable String id){
        ApiResponseDTO response = roleService.getRole(id);
        return  new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseDTO> getAllRole(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page,size);
        ApiResponseDTO response = roleService.getAllRole(pageable);
        return  new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponseDTO> deleteRole(@PathVariable String id){
        ApiResponseDTO response = roleService.deleteRole(id);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
