package ru.itmo.database.jpa.coursePaper_4;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class ClimbingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<Climber> climbers;
    private boolean isRecruiting;
    @ManyToOne
    private Mountain mountain;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public ClimbingGroup(List<Climber> climbers, boolean isRecruiting, Mountain mountain, LocalDateTime startTime, LocalDateTime endTime) {
        this.climbers = climbers;
        this.isRecruiting = isRecruiting;
        this.mountain = mountain;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ClimbingGroup() {

    }
}