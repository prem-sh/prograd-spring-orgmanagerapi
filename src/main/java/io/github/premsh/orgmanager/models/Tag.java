package io.github.premsh.orgmanager.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "tag_name")
    String tag;
}
