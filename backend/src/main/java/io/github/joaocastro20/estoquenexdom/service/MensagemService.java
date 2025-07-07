package io.github.joaocastro20.estoquenexdom.service;

import io.github.joaocastro20.estoquenexdom.domain.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensagemService {
    private final List<Mensagem> mensagens = new ArrayList<>();

    public void adicionarMensagem(Mensagem mensagem){
        mensagens.add(mensagem);
    }

    public List<Mensagem> listarMensagem(){
        return mensagens;
    }
}
