package br.com.basic.basic.tarefas;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificacaoService {

    private final String EXPO_PUSH_API_URL = "https://exp.host/--/api/v2/push/send";

    public void enviarNotificacao(String token, String titulo, String mensagem) {
        RestTemplate restTemplate = new RestTemplate();

        var notificacao = Map.of(
                "to", token,
                "title", titulo,
                "body", mensagem
        );

        try {
            restTemplate.postForEntity(EXPO_PUSH_API_URL, notificacao, String.class);
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}