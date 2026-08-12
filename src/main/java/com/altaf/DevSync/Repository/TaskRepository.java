package com.altaf.DevSync.Repository;

import com.altaf.DevSync.Model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByWorkSpaceId(Long workSpaceId);
}
