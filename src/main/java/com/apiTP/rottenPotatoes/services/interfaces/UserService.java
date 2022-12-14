package com.apiTP.rottenPotatoes.services.interfaces;

import com.apiTP.rottenPotatoes.dtos.User;
import com.apiTP.rottenPotatoes.dtos.VerificationToken;
import com.apiTP.rottenPotatoes.models.UserModel;

import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);

    void salvarTokenDeVerificacao(String token, User user);

    String validandoTokenDeVerificacao(String token);

    VerificationToken gerandoNovoTokenDeVerificacao(String oldtoken);

    User findUserByEmail(String email);

    void criandoSenhaResetTokenParaUsuario(User user, String token);

    String validandoNovaSenhaToken(String token);

    Optional<User> getUserByPasswordToken(String token);

    void changePassword(User user, String novaSenha);

    boolean verificandoSenhaAntiga(User user, String senhaAntiga);
}
