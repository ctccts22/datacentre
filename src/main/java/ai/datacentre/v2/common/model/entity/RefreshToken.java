package ai.datacentre.v2.common.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_REFRESH_TOKEN", indexes = {
        @Index(name = "index_username",  columnList = "username")
})
public class RefreshToken {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "username")
    private String username;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Builder
    public RefreshToken(String token, String username, Date expiryDate) {
        this.token = token;
        this.username = username;
        this.expiryDate = expiryDate;
    }
}
