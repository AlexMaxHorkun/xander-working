package com.alexandermaxgorkun.coolstory.entity.validator;

/**
 * Validators will be used to validate entities created by user.
 */
public interface Validator<T> {
    /**
     * Checks if given object is valid.
     */
    public boolean isValid(T object);
}
