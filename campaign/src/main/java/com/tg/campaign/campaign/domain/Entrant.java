package com.tg.campaign.campaign.domain;

import com.tg.campaign.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Entrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CAMPAIGN_ENTRANT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIGN_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    public Entrant(Campaign campaign, User user) {
        this.campaign = campaign;
        this.user = user;
    }
}
