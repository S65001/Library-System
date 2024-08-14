package maids.cc.library_management_system.service;

import lombok.RequiredArgsConstructor;
import maids.cc.library_management_system.entity.Patron;
import maids.cc.library_management_system.exception.ErrorCode;
import maids.cc.library_management_system.exception.RuntimeErrorCodedException;
import maids.cc.library_management_system.repo.PatronRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronService {
    private final PatronRepo patronRepo;
    public void addPatron(Patron patron){
        patronRepo.save(patron);
    }
    @CachePut(value = "patron_cache",key = "#id")
    public void updatePatron(Long id , Patron newPatronDetails){
        Patron patronDetails=patronRepo.findById(id).orElseThrow(()->new RuntimeErrorCodedException(ErrorCode.PATRON_NOT_FOUND,"patron is not found"));
        patronDetails=Patron.builder()
                .Id(patronDetails.getId())
                .name(newPatronDetails.getName())
                .contactInfo(newPatronDetails.getContactInfo()).build();
        patronRepo.save(patronDetails);
    }
    public List<Patron> getAllPatrons(){
        return patronRepo.findAll();
    }
    @Cacheable(value = "patron_cache",key = "#id")
    public Patron getPatron(Long id){
        return patronRepo.findById(id).orElseThrow(()->new RuntimeErrorCodedException(ErrorCode.PATRON_NOT_FOUND,"patron is not found"));
    }
    @CacheEvict(value = "patron_cache",key = "#id")
    public void deletePatron(Long id){
        patronRepo.deleteById(id);
    }
}
