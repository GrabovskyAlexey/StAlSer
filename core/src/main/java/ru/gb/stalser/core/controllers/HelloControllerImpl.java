package ru.gb.stalser.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.stalser.api.dto.ConfirmToken;
import ru.gb.stalser.api.dto.hello.HelloDto;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.core.controllers.interfaces.HelloController;
import ru.gb.stalser.core.utils.JwtTokenUtil;

import java.util.List;

@RestController
@RequestMapping("/${stalser.api.url}/hello")
@RequiredArgsConstructor
public class HelloControllerImpl implements HelloController {
    private final JwtTokenUtil jwtTokenUtil;
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

    @GetMapping("/get")
    public ResponseEntity<MessageDto> getToken(){
        final ConfirmToken build = ConfirmToken.builder()
                .code("test-code-for-token")
                .email("megapixar@gmail.com")
                .type(ConfirmToken.TokenType.REGISTER)
                .build();
        return ResponseEntity.ok(new MessageDto(jwtTokenUtil.generateConfirmationToken(build)));
    }

    @GetMapping("/test")
    public ResponseEntity<MessageDto> checkToken(@RequestParam String token){
        final ConfirmToken confirmToken = jwtTokenUtil.parseConfirmToken(token);
        System.out.println(confirmToken);
        return ResponseEntity.ok(new MessageDto("OK"));
    }
}
