package com.upvote.aismpro.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MyAlbumPK implements Serializable {
    private String userId;
    private String songId;
}