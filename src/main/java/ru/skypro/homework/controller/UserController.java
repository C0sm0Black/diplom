package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private final UserService userService;
    private final AuthService authService;


    @Operation(summary = "Обновление пароля", operationId = "setPassword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK"),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden")
    })
    @PostMapping("set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword) {

        if (authService.changePassword(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), newPassword)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    }

    @Operation(summary = "Получение информации об авторизованном пользователе", operationId = "getUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {

        Optional<UserDto> userOptional = userService.getUserByDtoByLogin(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());

    }

    @Operation(summary = "Обновление информации об авторизованном пользователе", operationId = "updateUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserUpdate.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized")
    })
    @PatchMapping("/me")
    public ResponseEntity<UserUpdate> updateUser(@RequestBody UserUpdate userUpdate) {

        Optional<UserUpdate> userUpdateOptional = userService.updateUserByUserUpdate(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), userUpdate);

        return userUpdateOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());

    }

    @Operation(summary = "Обновление аватара авторизованного пользователя", operationId = "updateUserImage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized")
    })
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestPart MultipartFile image) {

        if (userService.changeImage(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), image).isPresent()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
