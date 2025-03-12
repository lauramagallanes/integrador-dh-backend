package com.grupo1.pidh;

import com.grupo1.pidh.config.PasswordEncoderConfiguration;
import com.grupo1.pidh.entity.Usuario;
import com.grupo1.pidh.repository.UsuarioRepository;
import com.grupo1.pidh.service.IUsuarioService;
import com.grupo1.pidh.utils.enums.UsuarioRoles;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PidhApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(PidhApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner init (UsuarioRepository usuarioRepository, PasswordEncoderConfiguration passwordEncoderConfiguration){
		return args -> {
			if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()){
				Usuario admin = new Usuario(
						"Admin",
						"Admin",
						"admin@admin.com",
						passwordEncoderConfiguration.passwordEncoder().encode("Admin123!"),
						UsuarioRoles.ADMIN,
						true);
				usuarioRepository.save(admin);
			}
		};
	}
	@PostConstruct
	public void logActiveProfiles() {
		String[] profiles = environment.getActiveProfiles();
		if (profiles.length == 0) {
			System.out.println("⚠️ No hay perfiles activos. Usando el perfil por defecto.");
		} else {
			System.out.println("✅ Perfiles activos: " + String.join(", ", profiles));
		}
	}
}
