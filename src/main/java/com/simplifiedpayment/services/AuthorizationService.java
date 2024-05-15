package com.simplifiedpayment.services;

import com.simplifiedpayment.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Buscando usuário por email para autenticação: {}", email);
        UserDetails userDetails = this.userRepository.findByEmail(email);
        if (userDetails == null) {
            logger.error("Usuário com email {} não encontrado.", email);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        logger.info("Usuário encontrado com email: {}", email);
        return userDetails;
    }
}
