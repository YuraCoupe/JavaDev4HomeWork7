package ua.goit.projectmanagementsystem.model.converter;

public interface Converter<T, E> {
    E daoToDto(T t);
    T dtoToDao(E e);
}
