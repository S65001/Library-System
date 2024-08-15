package maids.cc.library_management_system.controller;

import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPatron() {
        Patron patron = new Patron();

        ResponseEntity<String> response = patronController.addPatron(patron);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(patronService, times(1)).addPatron(patron);
    }

    @Test
    void testGetAllPatrons() {
        Patron patron1 = new Patron();
        Patron patron2 = new Patron();
        List<Patron> patrons = Arrays.asList(patron1, patron2);

        when(patronService.getAllPatrons()).thenReturn(patrons);

        List<Patron> result = patronController.getAllPatrons();

        assertEquals(2, result.size());
        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    void testGetPatron() {
        Long patronId = 1L;
        Patron patron = new Patron();
        patron.setId(patronId);

        when(patronService.getPatron(patronId)).thenReturn(patron);

        ResponseEntity<Patron> response = patronController.getPatron(patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patronId, response.getBody().getId());
        verify(patronService, times(1)).getPatron(patronId);
    }

    @Test
    void testUpdatePatron() {
        Long patronId = 1L;
        Patron patron = new Patron();

        ResponseEntity<String> response = patronController.updatePatron(patronId, patron);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(patronService, times(1)).updatePatron(patronId, patron);
    }

    @Test
    void testDeletePatron() {
        Long patronId = 1L;

        ResponseEntity<String> response = patronController.deletePatron(patronId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
        verify(patronService, times(1)).deletePatron(patronId);
    }
}
