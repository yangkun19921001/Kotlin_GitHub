package com.devyk.common.ext;


import org.jetbrains.annotations.Nullable;

public class Result<T> {

    private T value;
    private Throwable error;

    public T getValue() {
        return value;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public T component1() {
        return value;
    }

    @Nullable
    public Throwable component2() {
        return error;
    }

    public static <T> Result<T> of(Throwable error){
        Result<T> result = new Result<>();
        result.error = error;
        return result;
    }

    public static <T> Result<T> of(T value){
        Result<T> result = new Result<>();
        result.value = value;
        return result;
    }
}
