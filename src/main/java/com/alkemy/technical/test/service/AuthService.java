package com.alkemy.technical.test.service;

import com.alkemy.technical.test.entities.Token;
import com.alkemy.technical.test.enumerations.TokenType;
import com.alkemy.technical.test.dtos.requests.LoginRequestDTO;
import com.alkemy.technical.test.dtos.requests.RegisterRequestDTO;
import com.alkemy.technical.test.dtos.responses.TokenResponseDTO;
import com.alkemy.technical.test.entities.Users;
import com.alkemy.technical.test.repository.ITokenRepository;
import com.alkemy.technical.test.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    public TokenResponseDTO register(RegisterRequestDTO registerRequestDTO){
        var user = Users.builder()
                .name(registerRequestDTO.name())
                .email(registerRequestDTO.email())
                .password(this.passwordEncoder.encode(registerRequestDTO.password()))
                .status(true)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
        var userSave = userRepository.save(user);
        var jwtToken = this.jwtService.generateToken(userSave);
        var jwtTokenRefresh = this.jwtService.generateRefreshToken(userSave);
        this.saveUserToken(userSave,jwtToken);
        return new TokenResponseDTO(jwtToken,jwtTokenRefresh);
    }

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.email(),
                        loginRequestDTO.password())
        );
        var user = this.userRepository.findByEmail(loginRequestDTO.email()).orElseThrow();
        var jwtToken = this.jwtService.generateToken(user);
        var refreshToken = this.jwtService.generateRefreshToken(user);
        this.revokeAllUserTokens(user);
        this.saveUserToken(user,jwtToken);
        return new TokenResponseDTO(jwtToken,refreshToken);
    }

    private void saveUserToken(Users user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        this.tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final Users user){
        final List<Token> validUserTokens = this.tokenRepository
                .findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());
        if(!validUserTokens.isEmpty()){
            for(final Token token: validUserTokens){
                token.setExpired(true);
                token.setRevoked(true);
            }
            this.tokenRepository.saveAll(validUserTokens);
        }
    }
}
