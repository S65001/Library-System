package maids.cc.library_management_system.service;

import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.PatronRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatronServiceTest {
    @InjectMocks
    private PatronService patronService;
    @Mock
    private PatronRepo patronRepo;
    List<Patron> patronList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patronList=new ArrayList<>();
        Patron patron1= new Patron(1L,"adel","email:loki76@gmail.com , phone number:022345678");
        Patron patronUpdate= new Patron(1L,"adel","email:adel@gmail.com , phone number:022658481");
        Patron patron2= new Patron(null,"maged","email:maged@gmail.com , phone number:022589807");
        patronList.add(patron1);
        patronList.add(patronUpdate);
        patronList.add(patron2);
    }
    @Test
    public void shouldUpdatePatronSuccessfully(){
        //given
        Patron oldPatron=patronList.get(0);
        Patron updatedPatron=patronList.get(1);
        when(patronRepo.findById(anyLong())).thenReturn(Optional.of(oldPatron));
        when(patronRepo.save(any(Patron.class))).thenReturn(updatedPatron);
        //when
        Patron returnedPatron=patronService.updatePatron(1L,updatedPatron);
        // then
        assertEquals(updatedPatron.getId(),returnedPatron.getId());
        assertEquals(updatedPatron.getName(),returnedPatron.getName());
        assertEquals(updatedPatron.getContactInfo(),returnedPatron.getContactInfo());
        verify(patronRepo,times(1)).findById(anyLong());
        verify(patronRepo,times(1)).save(any(Patron.class));

    }
    @Test
    public void shouldThrowPatronNotFoundOnUpdatePatron(){
        when(patronRepo.findById(2L)).thenThrow(new RuntimeErrorCodedException(ErrorCode.PATRON_NOT_FOUND,"patron is not found"));
        RuntimeErrorCodedException exception=assertThrows(RuntimeErrorCodedException.class,()->patronService.updatePatron(2L,any(Patron.class)));
        assertEquals("patron is not found",exception.getMessage());
    }
    @Test
    public void shouldGetPatronSuccessfully(){
        //given
        Patron patron=patronList.get(0);
        when(patronRepo.findById(1L)).thenReturn(Optional.of(patron));
        //when
        Patron theFoundPatron=patronService.getPatron(1L);
        //then
        assertEquals(patron.getId(),theFoundPatron.getId());
        assertEquals(patron.getName(),theFoundPatron.getName());
        assertEquals(patron.getContactInfo(),theFoundPatron.getContactInfo());

    }

}
