package kr.ac.kopo.polytable.member.dto;

import kr.ac.kopo.polytable.member.model.Store;
import lombok.Data;

@Data
public class SimpleMemberResponse {

    private String username;
    private String name;
    private String birth;
    private Store store;
}
