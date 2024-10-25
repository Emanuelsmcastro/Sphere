package com.chat.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.server.infra.entities.Chat;
import com.chat.server.infra.entities.enums.ChatType;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT c.* FROM chat_tb c " +
                   "JOIN chat_participants cp1 ON c.id = cp1.chat_id " +
                   "JOIN chat_participants cp2 ON c.id = cp2.chat_id " +
                   "WHERE c.chat_type = :chatType " +
                   "AND cp1.participant_uuid = :userUUID1 " +
                   "AND cp2.participant_uuid = :userUUID2",
           nativeQuery = true)
    Optional<Chat> getChatByChatTypeAndParticipantsUUID(@Param("chatType") ChatType chatType,
                                                        @Param("userUUID1") UUID userUUID1,
                                                        @Param("userUUID2") UUID userUUID2);
    
    @Query(value = "SELECT c.* FROM chat_tb c " +
    			   "JOIN chat_participants cp1 ON c.id = cp1.chat_id " +
    			   "WHERE c.uuid = :chatUuid " + 
    			   "AND cp1.participant_uuid = :senderUuid",
			   nativeQuery = true)
    Optional<Chat> findByUuidAndSenderUuid(@Param("chatUuid") UUID chatUuid,
    									   @Param("senderUuid") UUID senderUuid);
    
    Optional<Chat> findByUuid(UUID uuid);
}
