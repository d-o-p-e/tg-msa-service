package com.tg.campaign.user.domain;

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

    private String nickname;

    @Column(unique = true)
    private String email;

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
    public User(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.mileage = new Mileage(0);
    }

    public void withdraw() {
        mileage.verifyAtLeastBalance();
        this.mileage = new Mileage(this.mileage.inquiryAmounts() - 1);
    }
}
