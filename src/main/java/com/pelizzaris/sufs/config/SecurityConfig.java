package com.pelizzaris.sufs.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/{id}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/usuarios/{id}/desativar").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/usuarios/{id}/ativar").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/email").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/{id}").hasAuthority("SCOPE_MASTER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/roles").hasAuthority("SCOPE_MASTER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/auditorias").hasAuthority("SCOPE_MASTER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/auditorias/acao").hasAuthority("SCOPE_MASTER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/auditorias/usuario/{id}").hasAuthority("SCOPE_MASTER")

                        .requestMatchers(HttpMethod.POST, "/api/v1/turmas").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/turmas/{id}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/turmas/{id}/desativar").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/turmas/{id}/ativar").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/turmas").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN", "SCOPE_USUARIO")

                        .requestMatchers(HttpMethod.POST, "/api/v1/alunos").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/alunos/{id}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/alunos/{id}/desativar").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/alunos/{id}/ativar").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/alunos").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN", "SCOPE_USUARIO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/alunos/email").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN", "SCOPE_USUARIO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/alunos/{id}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN", "SCOPE_USUARIO")

                        .requestMatchers(HttpMethod.POST, "/api/v1/faltas").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/faltas/{id}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/faltas/{id}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/faltas").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/faltas/relatorio/data").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/faltas/relatorio/usuario/{usuarioId}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN", "SCOPE_USUARIO")
                        .requestMatchers(HttpMethod.GET, "/api/v1/faltas/relatorio/aluno/{alunoId}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/faltas/relatorio/aluno/{alunoId}/periodo").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/faltas/relatorio/turma/{turmaId}").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/faltas/relatorio/turma/{turmaId}/periodo").hasAnyAuthority("SCOPE_MASTER", "SCOPE_ADMIN")
                    .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable()) //ao subir na produção, excluir essa linha (USAR apenas no desenvolvimento)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        log.info("Security - Filtros de segurança aplicados com sucesso.");
        return http.build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
