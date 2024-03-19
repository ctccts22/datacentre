package ai.datacentre.v2.common.model.entity;

import ai.datacentre.v2.common.model.enums.Role;
import ai.datacentre.v2.common.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_MEMBERS",
        indexes = {
                @Index(name = "index_username",  columnList = "username", unique = true),
                @Index(name = "index_email", columnList = "email", unique = true)
        })
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "name", nullable = true, length = 45)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "rdate", nullable = true)
    private LocalDateTime rdate;
    // 수정 삭제
    @Column(name = "ldate", nullable = true)
    private LocalDateTime ldate;

    @Builder
    public Member(Long id, String username, String password, String email, String name, Role role, Status status, LocalDateTime rdate, LocalDateTime ldate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = status;
        this.rdate = rdate;
        this.ldate = ldate;
    }
}
