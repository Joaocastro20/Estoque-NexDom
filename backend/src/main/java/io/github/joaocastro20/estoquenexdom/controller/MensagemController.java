package io.github.joaocastro20.estoquenexdom.controller;

import io.github.joaocastro20.estoquenexdom.domain.Mensagem;
import io.github.joaocastro20.estoquenexdom.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @GetMapping
    public List<Mensagem> listarMensagens(){
        return mensagemService.listarMensagem();
    }
}
