package ru.skypro.homework.service;

public interface PermissionCheckService {

    boolean checkAdsUserName(String userLogin, Long adId);
    boolean checkCommentUserName(String userLogin, Long commentId);

}
