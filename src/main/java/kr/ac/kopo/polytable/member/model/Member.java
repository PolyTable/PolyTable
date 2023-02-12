package kr.ac.kopo.polytable.member.model;

import kr.ac.kopo.polytable.member.vo.RoleType;
import kr.ac.kopo.polytable.store.model.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
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

    @Builder
    public Member(String email, String username, String password, RoleType roleType , String name, LocalDate birthDate, String telNo) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleType = roleType;
        this.activated = true;
        this.name = name;
        this.birthDate = birthDate;
        this.telNo = telNo;
    }




    /**
     * 비즈니스 로직
     */

    public void addNewStore(Store store) {
        this.store = store;
    }

    public void removeStoreFromOwner() {
        this.store = null;
    }

    public void modifiedEmail(String email) {
        this.email = email;
    }

    public void modifiedPwd(String password) {
        this.password = password;

        LocalDateTime date = LocalDateTime.now();
        date.format(DateTimeFormatter.BASIC_ISO_DATE);
        this.lastModifiedPwdDate = date;
    }

    public void modifiedName(String name) {
        this.name = name;
    }

    public void modifiedBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void modifiedTelNo(String telNo) {
        this.telNo = telNo;
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
}
