package alura.api.challenge.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

@Service
public class GeraTextoDescritivoService {

    private static final String API_KEY = "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";
    public String geraTextoDescritivo(String destino){
        OpenAiService service = new OpenAiService(API_KEY);

        var pergunta = String.format(
                "Gere um resumo de até 200 caracteres sobre %s enfatizanfo características do local e argumentando porque é um bom local para viajar."
                , destino);

        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(pergunta)
                .maxTokens(500)
                .build();

        var response = service.createCompletion(request);
        return response.getChoices().get(0).getText();
    }
}
