package com.mb.checker.directory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Audited
public class Avatar extends Auditable<String>{

    @Id
    private String avatarUuid;

    @Column(unique = true)
    private String name;

    private String type;

    @Column(length = 1000)
    private byte[] picByte;


    public Avatar() {
        this.avatarUuid = UUID.randomUUID().toString();
    }

    public Avatar(String name, String type, byte[] picByte) {
        this();
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }


}
