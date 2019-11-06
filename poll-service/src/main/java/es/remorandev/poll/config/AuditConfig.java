package es.remorandev.poll.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuración para habilitar funcionalidades de auditoria para entidades de Spring Data
 * por ejemplo : @LastModifiedDate @CreatedDate
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {
    
}
