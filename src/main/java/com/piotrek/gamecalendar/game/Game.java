package com.piotrek.gamecalendar.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.piotrek.gamecalendar.game_release_date.GameReleaseDate;
import com.piotrek.gamecalendar.game_series.GameSeries;
import com.piotrek.gamecalendar.user.User;
import com.piotrek.gamecalendar.util.DateAudit;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Game extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<GameReleaseDate> releaseDates = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private GameSeries gameSeries;

    @ManyToOne
    private User addedBy;
}
