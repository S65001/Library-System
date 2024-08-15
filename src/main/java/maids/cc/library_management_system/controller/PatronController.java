package maids.cc.library_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Patron", description = "Operations related to patrons in the library system")
@SecurityRequirement(name = "bearerAuth")
public class PatronController {
    private final PatronService patronService;
    @Operation(summary = "Add a new patron",
            description = "Add a new patron to the library system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added the patron"),
                    @ApiResponse(responseCode = "400", description = "Invalid patron details")
            })
    @PostMapping
    public ResponseEntity<String> addPatron(@Valid @RequestBody Patron patron){
        patronService.addPatron(patron);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "Get all patrons",
            description = "Retrieve a list of all patrons in the library system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of patrons")
            })
    @GetMapping
    public List<Patron> getAllPatrons(){
        return patronService.getAllPatrons();
    }

    @Operation(summary = "Get patron by ID",
            description = "Retrieve details of a specific patron by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the patron"),
                    @ApiResponse(responseCode = "404", description = "Patron not found")
            })
    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatron(@PathVariable("id") Long id){
        return ResponseEntity.ok(patronService.getPatron(id));

    }

    @Operation(summary = "Update patron details",
            description = "Update details of a specific patron by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated the patron"),
                    @ApiResponse(responseCode = "400", description = "Patron not found")
            })
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatron(@PathVariable("id") Long id, @Valid @RequestBody Patron patron){
        patronService.updatePatron(id, patron);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "Delete patron by ID",
            description = "Delete a specific patron from the library system by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted the patron"),
                    @ApiResponse(responseCode = "400", description = "Patron not found")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatron(@PathVariable("id") Long id){
        patronService.deletePatron(id);
        return ResponseEntity.ok("success");
    }
}
