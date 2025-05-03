package br.com.basic.basic.tarefas;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(notificacao, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_API_URL, request, String.class);
            System.out.println("Resposta da API do Expo: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}