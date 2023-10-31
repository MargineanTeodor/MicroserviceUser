package UserBackend.service;

import UserBackend.DTO.UserDTO;
import UserBackend.mapper.UserMapper;
import UserBackend.model.Token;
import UserBackend.model.User;
import UserBackend.repository.TokenRepository;
import UserBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceToken {
    private final TokenRepository tokenRepository;
    public ServiceToken(TokenRepository tokenRepository)
    {
        this.tokenRepository= tokenRepository;
    }
    public Token addToken(String token)
    {
        Token token1 = new Token();
        token1.setToken(token);
        tokenRepository.save(token1);
        return token1;
    }
}
