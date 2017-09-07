package com.iplay.web.auth;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.component.mailer.Mailer;
import com.iplay.component.totp.TotpAuthenticator;
import com.iplay.configuration.mail.MailConfigurationProperties;
import com.iplay.configuration.security.jwtAuthentication.JwtFactory;
import com.iplay.configuration.security.jwtAuthentication.auth.UserContext;
import com.iplay.configuration.totp.TotpConfigurationProperties;
import com.iplay.dto.auth.JwtResponseDTO;
import com.iplay.dto.auth.TotpVerificationResponseDTO;
import com.iplay.entity.user.UserDO;
import com.iplay.service.auth.AuthService;
import com.iplay.service.user.UserService;
import com.iplay.vo.auth.AuthenticationVO;
import com.iplay.vo.auth.TotpVerificationRequestVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@EnableConfigurationProperties(MailConfigurationProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtFactory factory;
    
    @SuppressWarnings("unused")
	@Autowired
    private UserService userService;
    
    @Autowired
    private TotpAuthenticator authenticator;
    
    @Autowired
    private MailConfigurationProperties mailConfigurationProperties;
    
    @Autowired
    private TotpConfigurationProperties totpConfigurationProperties;
    
    @Autowired
    private Mailer mailer;

    @ApiOperation(notes="根据用户名密码返回一个token，以后每次请求受保护的API时添加一个header项：Authorization:Bearer token", value = "")
    @PostMapping("/api/auth/token")
    public JwtResponseDTO createAuthenticationToken(@ApiParam("用户名密码实体对象")@Valid@RequestBody AuthenticationVO param) throws AuthenticationException{
    	String username = param.getUsername(),password=param.getPassword();
    	Optional<UserDO> optionalUser = authService.authenticate(username,password);
    	optionalUser.orElseThrow(()->new BadCredentialsException("Authentication Failed. Username or Password not valid."));
    	UserDO user = optionalUser.get();
    	UserContext context = new UserContext(user.getId(), user.getUsername(), user.getRole());
    	String token = factory.generateToken(context);
    	return new JwtResponseDTO(token);
    }
    
    @ApiOperation(notes="根据邮箱获得一个动态密码", value = "")
    @GetMapping("/api/auth/totp") 
    public void createTOTP(@ApiParam("邮箱地址")@RequestParam String email){
    	long totpExpirationTime = totpConfigurationProperties.getTimeWindowSize()*
    			(totpConfigurationProperties.getAllowedFutureValidationWindows()
    					+totpConfigurationProperties.getAllowedPastValidationWindows());
    	String totpExpirationTimeString = (totpExpirationTime%60==0?
				(totpExpirationTime/60+"分鐘"):(totpExpirationTime+"秒"));
    	String totp = authenticator.generateTotpUsingBase64Decoder(email);
    	mailer.sendMail(mailConfigurationProperties.getSender(), email, mailConfigurationProperties.registrationEmail.getSubject()
    			,mailConfigurationProperties.registrationEmail.getContent()
    			.replace("{totp}", totp).replace("{totp-expiration-time}", totpExpirationTimeString));
    }
    
    @ApiOperation(notes="针对用户输入的动态密码进行校验,动态密码1分钟内有效", value = "")
    @PostMapping("/api/auth/totp") 
    public TotpVerificationResponseDTO verify(@ApiParam("邮箱和动态密码")@Valid@RequestBody TotpVerificationRequestVO request){
    	String email = request.getEmail(),totp = request.getTotp();
    	TotpVerificationResponseDTO result = new TotpVerificationResponseDTO();
    	if(authenticator.validateTotpUsingBase64Decoder(email, totp)){
    		result.setTotpValid(true);
    		result.setToken(factory.generateRegistrationToken(email));
    	}
		return result;
    }

}
