package com.CareerNexus_Backend.CareerNexus.repository;

import com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundDto;
import com.CareerNexus_Backend.CareerNexus.model.AssessmentRound;
import com.CareerNexus_Backend.CareerNexus.model.JobPost;
import com.CareerNexus_Backend.CareerNexus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<AssessmentRound, Long> {

    @Query("SELECT NEW com.CareerNexus_Backend.CareerNexus.dto.AssessmentRoundDto(" +
            "jp.postedBy.id, " +
            "jp.id, " +
            "conf.roundName, " +
            "conf.startTime, " +
            "conf.endTime, " +
            "conf.min_marks) " +
            "FROM AssessmentRound conf " +
            "INNER JOIN conf.jobPost jp " +
            "WHERE jp.id = :id") // User check has been removed
    List<AssessmentRoundDto> findAssessmentRoundsByJobId(@Param("id") Long id);

}
