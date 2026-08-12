package com.altaf.DevSync.Repository;

import com.altaf.DevSync.Model.WorkSpaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkSpaceMemberRepository extends JpaRepository<WorkSpaceMember, Long> {
    List<WorkSpaceMember> findAllByWorkSpaceId(Long workSpaceId);
    Boolean existsByWorkSpaceIdAndUserId(Long workSpaceId, Long id);

    Optional<WorkSpaceMember> findByWorkSpaceIdAndUserId(Long workSpaceId, Long id);


}
