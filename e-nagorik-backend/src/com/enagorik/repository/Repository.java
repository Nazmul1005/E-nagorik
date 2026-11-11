package com.enagorik.repository;
import java.util.*;
public interface Repository<T> { List<T> all(); Optional<T> find(String id); T save(T value); }
