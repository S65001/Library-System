package maids.cc.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.service.PatronService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patron")
@RequiredArgsConstructor
public class PatronController {
    private final PatronService patronService;
    @PostMapping
    public ResponseEntity<String> addPatron(@Valid @RequestBody Patron patron){
        patronService.addPatron(patron);
        return ResponseEntity.ok("success");
    }
    @GetMapping
    public List<Patron> getAllPatrons(){
        return patronService.getAllPatrons();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatron(@PathVariable("id") Long id){
        return ResponseEntity.ok(patronService.getPatron(id));

    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatron(@PathVariable("id") Long id, @Valid @RequestBody Patron patron){
        patronService.updatePatron(id, patron);
        return ResponseEntity.ok("success");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatron(@PathVariable("id") Long id){
        patronService.deletePatron(id);
        return ResponseEntity.ok("success");
    }
}
