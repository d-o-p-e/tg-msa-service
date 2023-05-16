package com.tg.user.user.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Entity
@Getter
@Table(name = "USERS")
@SecondaryTable(
        name = "MILEAGE",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "USER_ID")
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    @Column(unique = true)
    private String providerId;

    private String nickname;

    @Column(unique = true)
    private String email;

    private String profileImageUrl;

    @AttributeOverrides({
            @AttributeOverride(
                    name = "amounts",
                    column = @Column(table = "MILEAGE", name = "amounts")
            )
    })
    @Embedded
    private Mileage mileage;

    public User() {
    }

    @Builder
    public User(String providerId, String nickname, String email, String profileImageUrl) {
        this.providerId = providerId;
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.mileage = new Mileage();
    }
}
