package br.com.starter.domain.events;

import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkRepository;
import br.com.starter.domain.work.WorkStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkMonitorService {
    private final WorkRepository workRepository;
    private final ApplicationEventPublisher eventPublisher;

    public WorkMonitorService(WorkRepository workRepository, ApplicationEventPublisher eventPublisher) {
        this.workRepository = workRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 900000)
    public void checkPendingWorks() {
        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
        List<Work> pendingWorks = workRepository.findByStatusAndStartAtBefore(WorkStatus.PENDING, twoHoursAgo);

        for (Work work : pendingWorks) {
            eventPublisher.publishEvent(new WorkPendingTooLongEvent(this, work));
        }
    }

}
