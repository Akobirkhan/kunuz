package kunuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_history")
@EntityListeners(AuditingEntityListener.class)
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "message", columnDefinition = "text")
    private String message;
    @Column(name = "email")
    private String email;
    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
