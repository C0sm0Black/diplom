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
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.AdsService;

import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@AllArgsConstructor
@Tag(name = "Объявления", description = "AdController")
public class AdController {

    private final AdsService adsService;

    @Operation(summary = "Получение всех объявлений", operationId = "getAllAds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseWrapper.class)))
    })
    @GetMapping
    public ResponseEntity<ResponseWrapper<AdsDto>> getAllAds() {
        return ResponseEntity.ok(ResponseWrapper.of(adsService.getAllAds()));
    }


    @Operation(summary = "Добавление объявления", operationId = "addAd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdsDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<AdsDto> addAd(@RequestPart("image") MultipartFile multipartFile,
                                        @RequestPart("properties") CreateOrUpdateAd createAd) {

        AdsDto adsDto = adsService.createAd(SecurityContextHolder.getContext().getAuthentication().getName(),
                multipartFile,
                createAd);

        return ResponseEntity.status(HttpStatus.CREATED).body(adsDto);

    }

    @Operation(summary = "Получение информации об объявлении", operationId = "getAds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExtendedAdDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<ExtendedAdDto> getAds(@PathVariable("id") Long id) {

        Optional<ExtendedAdDto> optionalExtendedAdDto = adsService.getExtendedAdDto(id);
        return optionalExtendedAdDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @Operation(summary = "Удаление объявления", operationId = "removeAd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No Content"),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> removeAd(@PathVariable("id") Long id) {

        if (adsService.deleteByIdAd(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @Operation(summary = "Обновление информации об объявлении", operationId = "updateAds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdsDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @PatchMapping("{id}")
    public ResponseEntity<AdsDto> updateAds(@PathVariable("id") Long id,
                                            @RequestBody CreateOrUpdateAd updateAd) {

        Optional<AdsDto> optionalAdsDto = adsService.updateAd(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), id, updateAd);

        return optionalAdsDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @Operation(summary = "Обновление информации об объявлении", operationId = "updateAds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<AdsDto>> getAdsMe() {
        return ResponseEntity.ok(ResponseWrapper.of(adsService.getMyAds(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName())));
    }


    @Operation(summary = "Обновление картинки объявления", operationId = "updateImage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable("id") Long id, @RequestBody MultipartFile image) {

        Optional<String> responseStringOptional = adsService.changeImage(id, image);

        if (responseStringOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(responseStringOptional.get());

    }

}
