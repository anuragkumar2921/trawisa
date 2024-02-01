package com.backend.trawisa.model.entity;

import com.backend.trawisa.model.enumtype.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.app.base.project.constant.BaseFinalConstant.COLUM_DEFAULT.BOOLEAN_DEFAULT_FALSE;
import static com.backend.trawisa.constant.db.ColumnConstant.*;
import static com.backend.trawisa.constant.db.TableConstant.USER_TABLE;

@Entity
@Getter
@Setter

@AllArgsConstructor
@Table(name = USER_TABLE, uniqueConstraints = @UniqueConstraint(columnNames = {USER_NAME, "email"}))
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = USER_NAME, unique = true)
    private String userNames;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(length = 5)
    private int otp;

    @Column(length = 1)
    private int otpCount;

    @Column(columnDefinition =BOOLEAN_DEFAULT_FALSE)
    private Boolean isOtpUsed ;


    @Column(name = PROFILE_IMG)
    private String profileImage;

    @Column(name = FCM_TOKEN)
    private String fcmToken;

    @Column(name = ZIP_CODE)
    private int zipCode;

    private String location;

    @Lob
    @Column(length = 5000)
    private String description;

    @Column(name = IS_ACCOUNT_VERIFIED, columnDefinition =BOOLEAN_DEFAULT_FALSE)
    private boolean isAccountVerified;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = IS_PROFILE_COMPLETE, columnDefinition = BOOLEAN_DEFAULT_FALSE)
    private Boolean isProfileComplete;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = ROLE_ID)
    @JsonManagedReference
    private RoleEntity roleType;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = LOGIN_TYPE, referencedColumnName = LOGIN_TYPE)
    @JsonManagedReference
    private LoginTypeEntity loginType;

    @Column(name = SOCIAL_TOKEN)
    private String socialToken;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = CLASS_GRADE_ID)
    @JsonManagedReference
    private ClassGradeEntity class_grade;


    @OneToOne(mappedBy = USER_SCHEMA, cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserSettingEntity userSetting;

    @OneToMany(mappedBy = USER_SCHEMA, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TournamentEntity> tournaments = new ArrayList<>();

    @OneToMany(mappedBy = USER_SCHEMA, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VenueEntity> venueEntities = new ArrayList<>();

    @OneToMany(mappedBy = USER_SCHEMA, cascade = CascadeType.ALL)
    @JsonManagedReference
    List<TeamsEntity> teamsList = new ArrayList<>();

    @CreationTimestamp()
    @Column(name = CREATED_AT, updatable = false)
    private ZonedDateTime createdAt;


    @UpdateTimestamp
    @Column(name = UPDATED_AT)
    private ZonedDateTime updatedAt;


    // whenever user is created user setting is also created with default value
    public UserEntity() {
        this.userSetting = new UserSettingEntity(true, true, 15);
        this.userSetting.setUserEntity(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}