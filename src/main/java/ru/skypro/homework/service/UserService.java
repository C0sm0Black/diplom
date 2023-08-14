package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.dto.UserUpdate;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    /**
     * Регистрация пользователя
     *
     * @param register       {@link Register}
     * @param encodePassword объект {@link String}
     */
    void registerNewUser(Register register, String encodePassword);

    /**
     * Обновление пароля пользователя
     *
     * @param user {@link User}
     */
    void updatePassword(User user);

    /**
     * Получение DTO пользователя
     *
     * @param userLogin логин пользователя
     * @return объект {@link Optional<UserDto>}
     */
    Optional<UserDto> getUserByDtoByLogin(String userLogin);

    /**
     * Обновление информации о пользователе
     *
     * @param userLogin  логин пользователя
     * @param userUpdate {@link UserUpdate}
     * @return объект {@link Optional<UserUpdate>}
     */
    Optional<UserUpdate> updateUserByUserUpdate(String userLogin, UserUpdate userUpdate);

    /**
     * Получение пользователя по логину
     *
     * @param userLogin логин пользователя
     * @return объект {@link Optional<User>}
     */
    Optional<User> getUserByLogin(String userLogin);

    /**
     * Изменение картинки пользователя
     *
     * @param userLogin     логин пользователя
     * @param multipartFile {@link MultipartFile}
     * @return объект {@link Optional<User>}
     */
    Optional<User> changeImage(String userLogin, MultipartFile multipartFile);

    /**
     * Получение всех пользователей из БД
     *
     * @return объект {@link Collection<UserDetails>}
     */
    Collection<UserDetails> getAllUserDetails();

}
