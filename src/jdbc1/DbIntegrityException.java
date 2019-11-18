package jdbc1;

public class DbIntegrityException extends RuntimeException{

    public DbIntegrityException(String message) {
        super(message);
    }
}
