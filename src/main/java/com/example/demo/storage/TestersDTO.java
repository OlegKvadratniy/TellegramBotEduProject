package com.example.demo.storage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "testers")
@Data
@NoArgsConstructor
public class TestersDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "user_name")
    private String userName;

    public TestersDTO(long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
    }
}
