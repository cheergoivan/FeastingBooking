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

import com.iplay.component.mailer.AbstractMailer;
import com.iplay.component.totp.TotpAuthenticator;
import com.iplay.configuration.mail.MailConfigurationProperties;
import com.iplay.configuration.security.jwtAuthentication.JwtFactory;
import com.iplay.configuration.security.jwtAuthentication.auth.UserContext;
import com.iplay.dto.auth.AuthResponseDTO;
import com.iplay.dto.auth.TotpVerificationResponseDTO;
import com.iplay.dto.auth.UserDTO;
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
    private UserService userService;
    
    @Autowired
    private JwtFactory factory;
    
    @Autowired
    private TotpAuthenticator authenticator;
    
    @Autowired
    private MailConfigurationProperties mailConfigurationProperties;
    
    @Autowired
    private AbstractMailer mailer;

    @ApiOperation(notes="根据用户名（或邮箱）和密码返回user：id，role（ADMIN，USER，MANAGER）和token，以后每次请求受保护的API时添加一个header项：Authorization:Bearer token", value = "")
    @PostMapping("/api/auth/token")
    public AuthResponseDTO createAuthenticationToken(@ApiParam("用户名密码实体对象")@Valid@RequestBody AuthenticationVO param) throws AuthenticationException{
    	String username = param.getUsername(),password=param.getPassword();
    	Optional<UserDO> optionalUser = authService.authenticate(username,password);
    	optionalUser.orElseThrow(()->new BadCredentialsException("Authentication Failed. Username or Password not valid."));
    	UserDO user = optionalUser.get();
    	UserContext context = new UserContext(user.getId(), user.getUsername(), user.getRole());
    	String token = factory.generateToken(context);
    	return new AuthResponseDTO(new UserDTO(user.getId(), user.getRole().toString()), token);
    }
    
    @ApiOperation(notes="根据邮箱获得一个动态密码，动态密码默认2分钟内有效。此API仅在测试时开放。", value = "")
    @GetMapping("/api/auth/totp") 
    public String createTOTP(@ApiParam("邮箱地址")@RequestParam String email){
    	String totp = authenticator.generateTotpUsingBase64Decoder(email);
    	return totp;
    }
    
    @ApiOperation(notes="请求获得验证码， 此操作会向指定邮箱发送验证码。如果邮箱已注册，返回false", value = "")
    @GetMapping("/api/auth/applyForRegistrationEmail") 
    public boolean createTOTPAndSendEmail(@ApiParam("邮箱地址")@RequestParam String email){
    	if(userService.isEmailOccupied(email)){
    		return false;
    	}
    	String totp = authenticator.generateTotpUsingBase64Decoder(email);
    	mailer.sendMail(mailConfigurationProperties.getSender(), email, mailConfigurationProperties.registrationEmail.getSubject()
    			,mailConfigurationProperties.registrationEmail.getContent()
    			.replace("{totp}", totp));
    	return true;
    }
    
    @ApiOperation(notes="针对用户输入的动态密码进行校验,动态密码默认2分钟内有效", value = "")
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
