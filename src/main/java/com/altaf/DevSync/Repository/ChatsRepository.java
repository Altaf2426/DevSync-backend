package com.altaf.DevSync.Repository;

import com.altaf.DevSync.Model.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, Long> {
}
