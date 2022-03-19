package com.mb.checker.directory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mb.checker.shared.common.dto.Gender;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Audited
public class Student  extends Auditable<String>{

   @Id
   private String studentUuid;

   @Column(unique = true)
   private String registrationNumber;

   @NotEmpty
   private String firstName;

   @NotEmpty
   private String lastName;

   @Enumerated(EnumType.STRING)
   private Gender gender;

   @OneToOne()
   @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
   private Avatar avatar;

   @ManyToOne(cascade = CascadeType.PERSIST)
   private Faculty faculty;

   private BigDecimal solde = new BigDecimal(0);

   private boolean moratorium;

   private LocalDate moratoriumDelay;


   public Student() {
      this.studentUuid = UUID.randomUUID().toString();
      this.registrationNumber = RandomStringUtils.randomAlphanumeric(32);
   }

}
