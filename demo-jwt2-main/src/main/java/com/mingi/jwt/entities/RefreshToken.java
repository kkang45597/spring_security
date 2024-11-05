package com.mingi.jwt.entities;

import java.time.LocalDateTime;

import com.mingi.jwt.dto.TokenDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RefreshToken {
    @Id
    private String token;
    private String username;
    private LocalDateTime expiryDate;
}
