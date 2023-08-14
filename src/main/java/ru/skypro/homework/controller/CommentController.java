package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
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
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.CommentService;

import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@AllArgsConstructor
@Tag(name = "Комментарии", description = "CommentController")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Получение комментариев объявления", operationId = "getComments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseWrapper.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseWrapper<CommentDto>> getComments(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ResponseWrapper.of(commentService.listCommentsAdById(id)));
    }


    @Operation(summary = "Добавление комментария к объявлению", operationId = "addComment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @PostMapping("{id}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Long id,
                                                 @RequestBody CreateOrUpdateComment createComment) {

        Optional<CommentDto> optionalCommentDto = commentService.createComment(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), id, createComment);

        return optionalCommentDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @Operation(summary = "Удаление комментария", operationId = "deleteComment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK"),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @DeleteMapping("{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("adId") Long adId,
                                           @PathVariable("commentId") Long commentId) {

        if (commentService.deleteById(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), adId, commentId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @Operation(summary = "Обновление комментария", operationId = "updateComment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden"),
            @ApiResponse(responseCode = "404",
                    description = "Not found")
    })
    @PatchMapping("{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("adId") Long adId,
                                                    @PathVariable("commentId") Long commentId,
                                                    @RequestBody CreateOrUpdateComment updateComment) {

        Optional<CommentDto> optionalCommentDto = commentService.editComment(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(), adId, commentId, updateComment);

        return optionalCommentDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

}
