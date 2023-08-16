package ru.skypro.homework.service;

public interface PermissionCheckService {

    /**
     * Проверка на принадлежность объявления авторизованным пользователем
     *
     * @param userLogin логин пользователя {@link String}
     * @param adId      id объявления {@link Long}
     * @return true если объявление принадлежит авторизованному пользователю, false в обратном
     */
    boolean checkAdsUserName(String userLogin, Long adId);

    /**
     * Проверка на принадлежность комментария авторизованным пользователем
     *
     * @param userLogin логин пользователя {@link String}
     * @param commentId id комментария {@link Long}
     * @return true если комментарий принадлежит авторизованному пользователю, false в обратном
     */
    boolean checkCommentUserName(String userLogin, Long commentId);

}
