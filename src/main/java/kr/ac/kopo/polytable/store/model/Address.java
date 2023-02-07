package kr.ac.kopo.polytable.store.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String province;
    private String city;
    private String road;
    private String details;
}
