package kunuz.exp;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class AppBadException extends RuntimeException{
    public AppBadException(String message) {
        super(message);
    }
}
