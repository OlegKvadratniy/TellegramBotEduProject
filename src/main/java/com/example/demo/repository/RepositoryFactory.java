package com.example.demo.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class RepositoryFactory {
    @Autowired
    private TestersRepository testersRepository;
}
