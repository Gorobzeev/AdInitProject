package com.bcgdv.mobileservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @ColumnTransformer(forColumn = "email",
            read = "pgp_sym_decrypt(email, '{encryption.key}')",
            write = "pgp_sym_encrypt(?, '{encryption.key}')")
    @Column(name = "email", columnDefinition = "bytea")
    private String email;

    @ColumnTransformer(forColumn = "mobile_number", read = "pgp_sym_decrypt(mobile_number, '{encryption.key}')", write = "pgp_sym_encrypt(?, '{encryption.key}')")
    @Column(name = "mobile_number", columnDefinition = "bytea")
    private String mobileNumber;

    @ColumnTransformer(forColumn = "password", read = "pgp_sym_decrypt(password, '{encryption.key}')", write = "pgp_sym_encrypt(?, '{encryption.key}')")
    @Column(name = "password", columnDefinition = "bytea")
    private String password;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

}
