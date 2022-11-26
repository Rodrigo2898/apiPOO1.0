# Sobre a API

**TomatesPodres** tem como objetivo acessar informações sobre diversos filmes.

Link para a documentação: [https://app.swaggerhub.com/apis-docs/RODRIGOFEITOSARODRIG/apiPOO1.0/1.0.0](https://app.swaggerhub.com/apis-docs/RODRIGOFEITOSARODRIG/apiPOO1.0/1.0.0)

## Pacote entity

- ### **User.java**

 -   Cria a tabela tb_user

- ### **PassWordResetToken.java**
 - Cria tabela password_reset_token
 - Calcula o tempo para a expiração do token com o método calculoDataExpiracao(int tempoExpiração)

- ### **VerificationToken.class**
 - Cria tabela tb_verification

## Pacote repositories

- ### **Interface: UserRepository.java**

 -   -User findByEmail(String email)

- ### **Interface: PasswordResetTokenRepository.java**
 - PasswordResetToken findByToken(String token)

- ### **Interface: VerificatioinTokenRepository.java**
 - VerificationToken findByToken(String token)

  ## Pacote event

- ### **RegistrationCompleteEvent.java**

 -   Evento para o registro

## Pacote listener

- ### **RegistrationCompleteEventListener.java**

 -  No método onApplicationEvent(RegistrationCompleteEvent event) é criado o token de verificação para o usuário com o link
 - Manda o email para o usuário


## Pacote services

- ### **Interface: UserService.java**

 -   User registerUser(UserModel userModel);

 - void salvarTokenDeVerificacao(String token, User user);

 - String validandoTokenDeVerificacao(String token);

 - VerificationToken gerandoNovoTokenDeVerificacao(String oldtoken);

 - User findUserByEmail(String email);

 - void criandoSenhaResetTokenParaUsuario(User user, String token);

 - String validandoNovaSenhaToken(String token);

 - Optional<User> getUserByPasswordToken(String token);

 - void changePassword(User user, String novaSenha);

 - boolean verificandoSenhaAntiga(User user, String senhaAntiga);

- ### **Interface: EmailService.java**
 - void send(String to, String email);

- ### **UserServiceImpl.java**
 - Implementa a interface UserService
- ### **EmailService.java**
 - Implementa a interface EmailSender
 ## Pacote config
 - ### *SecurityConfig.java*
 - Segurança da API
## Pacote controllers

 - ### *RegistrationController.java*
 - Contém os enpoints para o funcionamento da API
   _________________

 -  Endpoints em ordem de uso:

	1)@PostMapping("/register")          -> Registra um usuário
        2) @GetMapping("/verifyRegistration") -> Verifica o registro
	3)@GetMapping("/resendVerifyToken")  -> Reenvia o token de verificação
	4)@PostMapping("/resetpassword")     -> Reseta senha 
        5)@PostMapping("/savePassword")      -> Salva senha
	6)@PostMapping("/changePassword")    -> Muda senha
	7)@PostMapping("/movies")            -> Adiciona um filme
	8)@GetMapping("/movies")             -> Busca todos os filmes
	9)@GetMapping("/movies/{"id"}")      -> Busca filme por id
	10)@DeleteMapping("/movies/{"id"}")  -> Deleta filme por id
	11)@PutMapping("/movies/{"id"}")     -> Atualiza a avaliação do filme
-   *Métodos da classe:*
    -   private String senhaResetTokenEmail(User user, String applicationUrl, String token) -> Envia por email 
      - .private void reenviandoTokenDeVerificacaoEmail(User user, String applicationUrl, VerificationToken verificationToken) -> Reenvia por email


*Tecnologias utilizadas*
 2. Java
 3. Spring Boot
 4. MySQL
