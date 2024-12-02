package com.mycompany.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "return_records")
public class ReturnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "borrow_record_id", nullable = false)
    private BorrowRecord borrowRecord;

    @Column(nullable = false)
    private LocalDate returnDate;

    // Getters and Setters
}
