package com.openclassrooms.mddapi.services.utils;

import java.util.Comparator;

import com.openclassrooms.mddapi.models.Post;

public class PostSortDescByCreatedDateComparator implements Comparator<Post> {

    @Override
    public int compare(Post o1, Post o2) {
        if (o1.getCreatedDate().getTime() <= o2.getCreatedDate().getTime()) {
            return 1;
        } else {
            return -1;
        }

    }

}
