package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;

public interface AuthService {

    /**
     * Логин пользователем
     *
     * @param userName логин пользователя {@link String}
     * @param password пароль {@link String}
     * @return true если прошло удачно, false в обратном случае
     */
    boolean login(String userName, String password);

    /**
     * Регистрация пользователем
     *
     * @param register данные регистрации {@link Register}
     * @return true если прошло удачно, false в обратном случае
     */
    boolean register(Register register);

    /**
     * Изминение пароля пользователем
     *
     * @param userLogin   логин пользователя {@link String}
     * @param newPassword новый пароль {@link NewPassword}
     * @return true если прошло удачно, false в обратном случае
     */
    boolean changePassword(String userLogin, NewPassword newPassword);

}
