package com.iplay.web.user;

import java.util.Optional;
import java.util.function.Predicate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.configuration.security.jwtAuthentication.JwtFactory;
import com.iplay.configuration.security.jwtAuthentication.auth.UserContext;
import com.iplay.configuration.security.jwtAuthentication.web.TokenExtractor;
import com.iplay.dto.ApiResponse;
import com.iplay.dto.ApiResponseMessage;
import com.iplay.dto.auth.RegistrationResponseDTO;
import com.iplay.entity.user.Role;
import com.iplay.entity.user.UserDO;
import com.iplay.service.user.UserService;
import com.iplay.vo.auth.AuthenticationVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtFactory factory;

	@ApiOperation(notes = "用户注册", value = "")
	@PostMapping
	public ApiResponse<?> register(
			@ApiParam("Authorization header: a token with email information") @RequestHeader(value = "Authorization") String headerValue,
			@ApiParam("用户名密码实体对象") @Valid @RequestBody AuthenticationVO param) {
		String username = param.getUsername(), password = param.getPassword();
		UsernamePredicate predicate = new UsernamePredicate();
		if (!predicate.test(username))
			return ApiResponse.createFailApiResponse(ApiResponseMessage.AUTH_USERNAME_INVALID);
		String token = TokenExtractor.extract(headerValue);
		String email = factory.deserializeRegistrationToken(token);
		if (userService.isEmailOccupied(email))
			return ApiResponse.createFailApiResponse(ApiResponseMessage.AUTH_EMAIL_OCCUPIED);
		Optional<UserDO> optionalUser = null;
		if (userService.isUsernameOccupied(username)
				|| !(optionalUser = userService.createOrdinaryUser(email, username, password)).isPresent())
			return ApiResponse.createFailApiResponse(ApiResponseMessage.AUTH_USERNAME_OCCUPIED);
		UserContext context = new UserContext(optionalUser.get().getId(), username, Role.USER);
		return ApiResponse.createSuccessApiResponse(
				new RegistrationResponseDTO(optionalUser.get().getId(), factory.generateToken(context)));
	}

	private class UsernamePredicate implements Predicate<String> {
		@Override
		public boolean test(String t) {
			return t.matches("[0-9a-zA-Z]{1,20}");
		}
	}

}
