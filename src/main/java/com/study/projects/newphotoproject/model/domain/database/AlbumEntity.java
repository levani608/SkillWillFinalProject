package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.AlbumStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity()
@Table//(uniqueConstraints = {@UniqueConstraint(columnNames = {"album_name", "server_id"})})
public class AlbumEntity extends TableDates{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private UserServerEntity serverEntity;

    @Column(name = "album_name", nullable = false)
    private String albumName;

    @Column(name = "album_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AlbumStatus albumStatus;

    @OneToMany(mappedBy = "photoAlbumEntity")
    private Set<PhotoEntity> photos;

    @OneToMany(mappedBy = "sharedAlbumEntity")
    private Set<ShareRightsEntity> sharedTo;

}
