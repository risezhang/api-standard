package com.risezhang.api.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liang on 09/03/2017.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Book {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(nullable = true)
    private String author;

    @Getter
    @Setter
    @Column(name = "create_time",nullable = true)
    private Date createTime;

}
