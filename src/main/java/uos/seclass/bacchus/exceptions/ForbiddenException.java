package uos.seclass.bacchus.exceptions;

public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException(String msg) {
        super(msg);
    }
}