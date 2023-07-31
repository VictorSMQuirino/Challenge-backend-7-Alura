package alura.api.challenge.infra.exception;

public class RegistroInativoException extends RuntimeException{
    public RegistroInativoException(String message){
        super(message);
    }
}
