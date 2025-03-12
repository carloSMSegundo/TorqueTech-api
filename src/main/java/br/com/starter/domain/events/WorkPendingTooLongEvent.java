package br.com.starter.domain.events;

import br.com.starter.domain.work.Work;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WorkPendingTooLongEvent extends ApplicationEvent {
    private final Work work;

    public WorkPendingTooLongEvent(Object source, Work work) {
        super(source);
        this.work = work;
    }

}
