package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAdDto;

import java.util.List;
import java.util.Optional;

public interface AdsService {

    /**
     * Создание объявления
     *
     * @param userLogin     логин пользователя
     * @param multipartFile объект {@link  MultipartFile}
     * @param createAd      объект {@link CreateOrUpdateAd}
     * @return объект {@link AdsDto}
     */
    AdsDto createAd(String userLogin, MultipartFile multipartFile, CreateOrUpdateAd createAd);

    /**
     * Получение всех объявлений в List</>
     *
     * @return объект {@link List<AdsDto>}
     */
    List<AdsDto> getAllAds();

    /**
     * Получение DTO объявления ({@link ExtendedAdDto}) по его id
     *
     * @param id {@link Long}
     * @return объект {@link Optional<ExtendedAdDto>}
     */
    Optional<ExtendedAdDto> getExtendedAdDto(Long id);

    /**
     * Удаление объявления по его id
     *
     * @param userLogin {@link String}
     * @param id        {@link Long}
     * @return true если удалилось объявление, false если это не возможно
     */
    boolean deleteByIdAd(String userLogin, Long id);

    /**
     * Обновление объявления ({@link ExtendedAdDto}) по его id
     *
     * @param userLogin {@link String}
     * @param id        {@link Long}
     * @param updateAd  {@link CreateOrUpdateAd}
     * @return объект {@link Optional<AdsDto>}
     */
    Optional<AdsDto> updateAd(String userLogin, Long id, CreateOrUpdateAd updateAd);

    /**
     * Получение всех объявлений пользователя в List</>
     *
     * @param userLogin {@link String}
     * @return объект {@link List<AdsDto>}
     */
    List<AdsDto> getMyAds(String userLogin);

    /**
     * Изменение картинки объявления по id
     *
     * @param id            {@link Long}
     * @param multipartFile {@link MultipartFile}
     * @return объект {@link Optional<String>}
     */
    Optional<String> changeImage(Long id, MultipartFile multipartFile);

}
