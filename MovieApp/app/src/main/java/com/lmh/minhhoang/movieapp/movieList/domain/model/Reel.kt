package com.lmh.minhhoang.movieapp.movieList.domain.model

import java.lang.reflect.Constructor
 class Reel {
     var url: String? = null
     var caption: String? = null
     constructor()
     constructor(url: String?, caption: String?) {
         this.url = url
         this.caption = caption
     }


 }