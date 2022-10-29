package com.highgeupsik.backend.core.model.school;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@TableGenerator(
    name = "SCHOOL_SEQ_GENERATOR",
    table = "MY_SEQUENCES",
    pkColumnValue = "SCHOOL_SEQ", allocationSize = 500
)
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SCHOOL_SEQ_GENERATOR")
    @Column(name = "school_id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "school_code", nullable = false)
    private String schoolCode;

    @Column(name = "region_code", nullable = false)
    private String regionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private Region region;

    @Column(name = "homepage_url")
    private String homepageUrl;

    public School(String name, String schoolCode, String regionCode, String region, String homepageUrl) {
        this.name = name;
        this.schoolCode = schoolCode;
        this.regionCode = regionCode;
        this.region = Region.valueOf(region);
        this.homepageUrl = homepageUrl;
    }
}
