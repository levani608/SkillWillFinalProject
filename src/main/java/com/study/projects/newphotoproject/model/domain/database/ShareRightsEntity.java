package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.ShareRightStatus;
import com.study.projects.newphotoproject.model.enums.ShareRightType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity(name = "share_rights")
@Getter
@Setter
@RequiredArgsConstructor
public class ShareRightsEntity extends TableDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_right_id")
    private Long shareRightId;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private UserEntity fromUserEntity;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private UserEntity toUserEntity;

    @ManyToOne
    @JoinColumn(name = "shared_album_id")
    private AlbumEntity sharedAlbumEntity;

    @Column(name = "share_right_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShareRightType shareRightType;

    @Column(name = "share_right_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShareRightStatus shareRightstatus;

}


