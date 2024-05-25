package kunuz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "sms_histoty")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "message", columnDefinition = "text")
    private String message;
    @Column(name = "phone")
    private String phone;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
