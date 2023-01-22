package ru.gb.stalser.core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.gb.stalser.api.dto.hello.HelloDto;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.core.controllers.interfaces.HelloController;

import java.util.List;

@Controller
public class HelloControllerImpl implements HelloController {
    @Override
    public ResponseEntity<List<HelloDto>> getAllHello() {
        return null;
    }

    @Override
    public ResponseEntity<HelloDto> getHelloById(final Long id) {
        return null;
    }

    @Override
    public ResponseEntity<MessageDto> addHello(final HelloDto hello) {
        return null;
    }

    @Override
    public ResponseEntity<MessageDto> updateHello(final Long id, final HelloDto hello) {
        return null;
    }

    @Override
    public ResponseEntity<MessageDto> deleteHello(final Long id) {
        return null;
    }
}
