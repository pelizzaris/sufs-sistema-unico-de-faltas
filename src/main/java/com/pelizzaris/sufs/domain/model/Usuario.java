package com.pelizzaris.sufs.domain.model;

import com.pelizzaris.sufs.domain.dto.LoginRequestDTO;
import com.pelizzaris.sufs.domain.model.util.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario extends Pessoa{

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_usuario_role",
        joinColumns = @JoinColumn(name = "pessoa_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> role;

    public Set<Roles> getRoles() {
        return role;
    }

    public void setRoles(Set<Roles> role) {
        this.role = role;
    }

    @Column(name = "senha_usuario", nullable = false)
    private String senha;

    public boolean isLoginCorrect(LoginRequestDTO loginRequestDto, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequestDto.senha(), this.senha);
    }

    public Usuario get() {
        return this;
    }
}
