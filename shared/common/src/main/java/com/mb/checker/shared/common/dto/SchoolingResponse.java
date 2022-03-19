package com.mb.checker.shared.common.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SchoolingResponse {
    private FeesStatus feesStatus;
    private String firstName;
    private String lastName;
    private BigDecimal solde;
    private String registrationNumber;
    private short level;
    private Gender gender;
    private Period period;
    private String studentUuid;
    private String sector;
    private byte[] avatar;

    private SchoolingResponse() {
    }

    public SchoolingResponse(SchoolingResponseBuilder builder) {
        this.feesStatus = builder.feesStatus;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.solde = builder.solde;
        this.registrationNumber = builder.registrationNumber;
        this.level = builder.level;
        this.gender = builder.gender;
        this.period = builder.period;
        this.studentUuid = builder.studentUuid;
        this.sector = builder.sector;
        this.avatar = builder.avatar;
    }

    public static class SchoolingResponseBuilder{

        private FeesStatus feesStatus;
        private String firstName;
        private String lastName;
        private BigDecimal solde;
        private final String registrationNumber;
        private short level;
        private Gender gender;
        private Period period;
        private final String studentUuid;
        private String sector;
        private byte[] avatar;


        public SchoolingResponseBuilder(String registrationNumber,String studentUuid) {
            this.registrationNumber = registrationNumber;
            this.studentUuid = studentUuid;
        }

        public SchoolingResponseBuilder hasFeesStatus(FeesStatus feesStatus) {
            this.feesStatus = feesStatus;
            return this;
        }

        public SchoolingResponseBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public SchoolingResponseBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public SchoolingResponseBuilder hasSolde(BigDecimal solde) {
            this.solde = solde;
            return this;
        }

        public SchoolingResponseBuilder atLevel(short level) {
            this.level = level;
            return this;
        }

        public SchoolingResponseBuilder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public SchoolingResponseBuilder setPeriod(Period period) {
            this.period = period;
            return this;
        }

        public SchoolingResponseBuilder setSector(String sector) {
            this.sector = sector;
            return this;
        }

        public SchoolingResponseBuilder hasAvatar(byte[] avatar) {
            this.avatar = avatar;
            return this;
        }

        public SchoolingResponse build(){
            return new SchoolingResponse(this);
        }
    }

}
