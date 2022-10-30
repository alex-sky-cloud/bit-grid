package grid.bit.controller.validateutils;

import grid.bit.exceptionhandlers.exceptions.IdConstraintExceptions;

public class IdValidateUtils {

    public static void validateId (Long id){

        int minValId = 1;

        if(id < minValId){
            throw new IdConstraintExceptions("A 'ID' must not be less than 1 or equal to 0");
        }
    }
}
