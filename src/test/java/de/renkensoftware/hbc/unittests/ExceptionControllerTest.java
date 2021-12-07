package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.exception.ExceptionController;
import de.renkensoftware.hbc.exception.ResponseError;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionControllerTest {

    private final ExceptionController exceptionController = new ExceptionController();

    @Test
    void handleUserNotFoundException() {
        ResponseEntity<ResponseError> response = exceptionController.handleUserNotFoundException();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(response.getBody()).message()).isEqualTo("User not found");
        assertThat(response.getBody().code()).isEqualTo(1);
    }
}