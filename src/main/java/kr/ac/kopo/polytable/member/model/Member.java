package kr.ac.kopo.polytable.member.model;

import kr.ac.kopo.polytable.member.enums.Auth;
import kr.ac.kopo.polytable.member.enums.Level;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String loginId;
    @Column(nullable = false)
    private String pwd;
    private LocalDateTime lastModifiedPwdDate;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Auth auth;

    private String name;
    private LocalDateTime birthDate;

    @Column(unique = true, nullable = false)
    private String telNo;

    @Builder
    public Member(String email, String loginId, String pwd, Level level, Auth auth, String name, LocalDateTime birthDate, String telNo) {
        this.email = email;
        this.loginId = loginId;
        this.pwd = pwd;
        this.level = level;
        this.auth = auth;
        this.name = name;
        this.birthDate = LocalDateTime.parse(birthDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        this.telNo = telNo;
    }


    public static Member createAdminAccount(String loginId, String pwd, String name) {
        Member admin = new Member();
        admin.loginId = loginId;
        admin.pwd = pwd;
        admin.name = name;
        admin.level = Level.ADMIN;
        admin.auth = Auth.Y;

        return admin;
    }

    /**
     * 비즈니스 로직
     */

    public void modifiedEmail(String email) {
        this.email = email;
    }

    public void modifiedPwd(String pwd) {
        this.pwd = pwd;

        LocalDateTime date = LocalDateTime.now();
        date.format(DateTimeFormatter.BASIC_ISO_DATE);
        this.lastModifiedPwdDate = date;
    }

    public void modifiedName(String name) {
        this.name = name;
    }

    public void modifiedBirthDate(LocalDateTime birthDate) {
        this.birthDate = LocalDateTime.parse(birthDate.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    public void modifiedTelNo(String telNo) {
        this.telNo = telNo;
    }
}
