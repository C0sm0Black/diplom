package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.service.PermissionCheckService;

@Service
@RequiredArgsConstructor
public class PermissionCheckServiceImpl implements PermissionCheckService {

    private final AdsRepository adsRepository;
    private final CommentsRepository commentsRepository;

    @Override
    public boolean checkAdsUserName(String userLogin, Long adId) {
        return adsRepository.findById(adId).map(ad -> ad.getAuthor().getEmail().equals(userLogin)).orElse(false);
    }

    @Override
    public boolean checkCommentUserName(String userLogin, Long commentId) {
        return commentsRepository.findById(commentId)
                .map(comment -> comment.getUser().getEmail().equals(userLogin)).orElse(false);
    }

}
