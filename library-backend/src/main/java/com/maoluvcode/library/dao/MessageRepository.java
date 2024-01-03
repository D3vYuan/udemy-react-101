package com.maoluvcode.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maoluvcode.library.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
