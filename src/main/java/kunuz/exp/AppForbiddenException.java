package kunuz.exp;

public class AppForbiddenException extends RuntimeException{
    public AppForbiddenException(String message){
        super(message);
    }
}
