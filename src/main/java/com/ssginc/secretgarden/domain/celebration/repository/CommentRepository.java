package com.ssginc.secretgarden.domain.celebration.repository;

import com.ssginc.secretgarden.domain.celebration.dto.CelebrationRankingDto;
import com.ssginc.secretgarden.domain.celebration.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c.member.company.name, COUNT(c) as commentCount " +
            "FROM Comment c " +
            "WHERE c.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY c.member.company.name " +
            "ORDER BY commentCount DESC")
    List<Object[]> findTopCommentingCompaniesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
