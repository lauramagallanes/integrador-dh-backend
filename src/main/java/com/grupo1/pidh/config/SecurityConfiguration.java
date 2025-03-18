package com.grupo1.pidh.config;

import org.apache.http.auth.AUTH;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter){
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                auth -> {
                    //AuthenticationController
                    auth.antMatchers("/auth/**").permitAll();

                    //CaracteristicaController
                    auth.antMatchers(HttpMethod.POST, "/caracteristica/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.DELETE, "/caracteristica/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/caracteristica/**").permitAll();

                    //CategoriaController
                    auth.antMatchers(HttpMethod.POST, "/categoria/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.DELETE, "/categoria/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/categoria/**").permitAll();

                    //ProductoController
                    auth.antMatchers(HttpMethod.POST, "/producto/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.PUT, "/producto/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.DELETE, "/producto/**").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/producto/**").permitAll();

                    //UsuarioController
                    auth.antMatchers(HttpMethod.PUT, "/usuario/modificarusuariorole").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.PUT, "/usuario/modificarusuario").authenticated();
                    auth.antMatchers(HttpMethod.GET, "/usuario/listar").hasAuthority("ADMIN");
                    auth.antMatchers(HttpMethod.GET, "/usuario/**").authenticated();
                    auth.antMatchers(HttpMethod.DELETE, "/usuario/**").authenticated();

                    //DisponibilidadProductoController
                    auth.antMatchers(HttpMethod.GET, "/disponibilidad/**").permitAll();

                    //ReservaController
                    auth.antMatchers(HttpMethod.POST, "/reserva/").authenticated();
                    auth.antMatchers(HttpMethod.GET, "/reserva/resena/**").permitAll();

                    auth.antMatchers("/swagger-ui/**").permitAll();
                    auth.antMatchers("/v3/api-docs/**").permitAll();

                    auth.anyRequest().hasAuthority("ADMIN");
                })
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .cors(cors -> cors.configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("*")); // Permitir frontend
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            config.setAllowedHeaders(List.of("*"));
                            return config;
                        }))
                .build();
    }
}
