package kr.ac.kopo.polytable.reservationtime.model;

import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.reservation.model.Reservation;
import kr.ac.kopo.polytable.reservationtime.error.MaximumHeadsOverCapacityException;
import kr.ac.kopo.polytable.store.model.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_time_id")
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @Column(nullable = false)
    private Integer maximumHeads;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "reservationTime")
    private List<Reservation> reservations = new ArrayList<>();


    @Builder
    public ReservationTime(LocalTime startTime, LocalTime endTime, Integer maximumHeads) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumHeads = maximumHeads;
    }

    /**
     * core
     */


    public void addNewMember(Member member) {
        this.member = member;
    }

    public void updateReservationTime(ReservationTime request) {
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.maximumHeads = request.getMaximumHeads();
    }

    public void validMaximumHeads(Integer headCount) {
        if (this.maximumHeads < headCount) {
            throw new MaximumHeadsOverCapacityException();
        }
    }
}
