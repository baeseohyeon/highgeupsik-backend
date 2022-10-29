package com.highgeupsik.backend.api.controller.user;

import static org.springframework.http.HttpStatus.OK;

import com.highgeupsik.backend.api.controller.ApiResult;
import com.highgeupsik.backend.api.controller.ApiUtils;
import com.highgeupsik.backend.api.resolver.LoginUser;
import com.highgeupsik.backend.api.service.user.UserQueryService;
import com.highgeupsik.backend.api.service.user.UserService;
import com.highgeupsik.backend.core.dto.user.UserResDTO;
import com.highgeupsik.backend.core.dto.user.UserUpdateForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserProfileController {

	private final UserService userService;
	private final UserQueryService userQueryService;

	@ApiOperation(value = "내정보 조회")
	@ResponseStatus(OK)
	@GetMapping
	public ApiResult<UserResDTO> userDetails(@LoginUser Long userId) {
		return ApiUtils.success(userQueryService.findById(userId));
	}

	@ApiOperation(value = "내정보 수정")
	@ResponseStatus(OK)
	@PatchMapping
	public void updateUser(@LoginUser Long userId, @RequestBody UserUpdateForm userUpdateForm) {
		userService.updateUser(userId, userUpdateForm.getGrade(), userUpdateForm.getClassNum(),
			userUpdateForm.getStudentCardImage(), userUpdateForm.getRegion(), userUpdateForm.getSchoolName());
	}
}
