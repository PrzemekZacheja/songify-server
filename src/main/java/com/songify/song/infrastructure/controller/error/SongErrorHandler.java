package com.songify.song.infrastructure.controller.error;

import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.infrastructure.controller.SongRestController;
import com.songify.song.infrastructure.controller.dto.response.DeleteSongResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = SongRestController.class)
@Log4j2
public class SongErrorHandler {

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DeleteSongResponseDto handleSongNotFoundException(SongNotFoundException songNotFoundException) {
        log.error(songNotFoundException.getClass()
                                       .getSimpleName() + " while accessing a song.");
        return new DeleteSongResponseDto(songNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}