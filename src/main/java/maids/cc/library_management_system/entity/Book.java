package maids.cc.library_management_system.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;


@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    private LocalDateTime publicationDate;
    @Column(nullable = false,unique = true)
    private String ISBN;
    private Integer quantity;
    private Integer borrowed;
}
