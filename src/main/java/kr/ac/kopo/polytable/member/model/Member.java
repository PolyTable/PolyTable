package kr.ac.kopo.polytable.member.model;

import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.dto.RoleType;
import kr.ac.kopo.polytable.reservation.model.Reservation;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private LocalDateTime lastModifiedPwdDate;

    @Embedded
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Column(name = "activated")
    private boolean activated;

    private String name;
    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String telNo;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ReservationTime> reservationTimes = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String username, String password, LocalDateTime lastModifiedPwdDate, Store store, RoleType roleType, boolean activated, String name, LocalDate birthDate, String telNo, List<Reservation> reservations, List<ReservationTime> reservationTimes) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.lastModifiedPwdDate = lastModifiedPwdDate;
        this.store = store;
        this.roleType = roleType;
        this.activated = activated;
        this.name = name;
        this.birthDate = birthDate;
        this.telNo = telNo;
        this.reservations = reservations;
        this.reservationTimes = reservationTimes;
    }


    /**
     * 비즈니스 로직
     */

    public void changeNewInfo(ModifiedRequest request) {
        this.email = request.getEmail();
        if (request.getPassword() != null) {
            this.modifiedPwd(request.getPassword());
        }
        this.birthDate = request.getBirthDate();
        this.name = request.getName();
        this.telNo = request.getTelNo();
        this.store = request.getStore();
    }

    public void modifiedPwd(String password) {
        this.password = password;

        LocalDateTime date = LocalDateTime.now();
        date.format(DateTimeFormatter.BASIC_ISO_DATE);
        this.lastModifiedPwdDate = date;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void activatedAccount() {
        this.activated = true;
    }

    public void turnOffAccount() {
        this.activated = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
