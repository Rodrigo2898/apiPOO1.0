package com.apiTP.tomatespodres.controllers;

import com.apiTP.tomatespodres.entity.User;
import com.apiTP.tomatespodres.entity.VerificationToken;
import com.apiTP.tomatespodres.event.RegistrationCompleteEvent;
import com.apiTP.tomatespodres.models.PasswordModel;
import com.apiTP.tomatespodres.models.UserModel;
import com.apiTP.tomatespodres.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Sucesso";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validandoTokenDeVerificacao(token);
        if(result.equalsIgnoreCase("valid")) {
            return "Verificação feita com sucesso";
        }
        return "Falha na verificação";
    }

    @GetMapping("/resendVerifyToken")
    public String reenviandoTokenDeVerificacao(@RequestParam("token") String oldtoken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.gerandoNovoTokenDeVerificacao(oldtoken);
        User user = verificationToken.getUser();
        reenviandoTokenDeVerificacaoEmail(user, applicationUrl(request), verificationToken);
        return "link de verificacao enviado";
    }

    @PostMapping("/resetpassword")
    public String redefinindoSenha(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if(user != null) {
            String token = UUID.randomUUID().toString();
            userService.criandoSenhaResetTokenParaUsuario(user, token);
            url = senhaResetTokenEmail(user, applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String salvandoSenha(@RequestParam("token") String token,
                                @RequestBody PasswordModel passwordModel) {
        String result = userService.validandoNovaSenhaToken(token);
        if(!result.equalsIgnoreCase("valid")) {
            return "Token inválido";
        }
        Optional<User> user = userService.getUserByPasswordToken(token);
        if(user.isPresent()) {
            userService.changePassword(user.get(), passwordModel.getNovaSenha());
            return "Senha redefinida com sucesso";
        }
        else {
            return "Senha não redefinida";
        }
    }

    private String senhaResetTokenEmail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;

        //enviaVerificacaoPorEmail()
        log.info("Clique no link para verificar sua conta: {}", url);
        return url;
    }

    private void reenviandoTokenDeVerificacaoEmail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

        //enviaVerificacaoPorEmail()
        log.info("Clique no link para redefinir sua senha: {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }


}
