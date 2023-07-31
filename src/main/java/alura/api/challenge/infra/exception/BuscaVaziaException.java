package alura.api.challenge.infra.exception;

public class BuscaVaziaException  extends RuntimeException{
    public BuscaVaziaException(String message){
        super(message);
    }
}
