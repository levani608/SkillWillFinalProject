package com.study.projects.newphotoproject.model.domain.database;

import com.study.projects.newphotoproject.model.enums.PhotoStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



@Entity(name = "photos")
@Table//(uniqueConstraints = {@UniqueConstraint(columnNames = {"photo_name", "photo_album_id"})})
@Getter
@Setter
@RequiredArgsConstructor
public class PhotoEntity extends TableDates{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @ManyToOne
    @JoinColumn(name = "photo_album_id")
    private AlbumEntity photoAlbumEntity;

    @Column(name = "photo_name", nullable = false)
    private String photoName;

    @Column(name = "photo_uri", nullable = false)
    private String photoUri;

    @Column(name = "photo_size", nullable = false)
    private Double photoSize;

    @Column(name = "photo_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PhotoStatus photoStatus;



}
