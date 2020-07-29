package ar.com.jlv.api.user.exceptions;

import java.util.Objects;

public class UserNotFoundException extends UserException{
	private static final long serialVersionUID = 1L;
    private static final String MESSAGE_DEFAULT = "User Not Found";

    @Override
    public String getMessage() {
        if (Objects.nonNull(super.getMessage())) {
            return super.getMessage();
        }
        return MESSAGE_DEFAULT;
    }
}
