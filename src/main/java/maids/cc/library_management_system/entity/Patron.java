package maids.cc.library_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="patrons")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String name;
    @Size(max=300,message = "contact info can't exceed 300 character")
    private String contactInfo;
}
