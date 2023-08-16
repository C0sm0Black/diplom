package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateComment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    /**
     * Получение всех комментариев, по id объявления, в List</>
     *
     * @param id {@link Long}
     * @return объект {@link List<CommentDto>}
     */
    List<CommentDto> listCommentsAdById(Long id);

    /**
     * Создание комментария, по id объявления
     *
     * @param userLogin     {@link String}
     * @param id            {@link Long}
     * @param createComment {@link CreateOrUpdateComment}
     * @return объект {@link Optional<CommentDto>}
     */
    Optional<CommentDto> createComment(String userLogin, Long id, CreateOrUpdateComment createComment);

    /**
     * Удаление комментария по его id и id объявления
     *
     * @param userLogin {@link String}
     * @param adId      {@link Long}
     * @param commentId {@link Long}
     * @return true, если комментарий удален, false, если этого не произошло
     */
    boolean deleteById(String userLogin, Long adId, Long commentId);

    /**
     * Редактирование комментария по его id и по id объявления
     *
     * @param userLogin     {@link String}
     * @param adId          {@link Long}
     * @param commentId     {@link Long}
     * @param updateComment {@link CreateOrUpdateComment}
     * @return объект {@link Optional<CommentDto>}
     */
    Optional<CommentDto> editComment(String userLogin, Long adId, Long commentId, CreateOrUpdateComment updateComment);

}
