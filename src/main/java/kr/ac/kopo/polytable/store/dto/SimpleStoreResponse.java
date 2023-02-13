package kr.ac.kopo.polytable.store.dto;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SimpleStoreResponse implements Serializable {

    private String storeName;

    private LocalDate foundedDate;

    private String ownerName;

}
