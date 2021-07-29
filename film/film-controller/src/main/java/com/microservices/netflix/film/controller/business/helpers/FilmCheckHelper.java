package com.microservices.netflix.film.controller.business.helpers;

import com.microservices.netflix.common.entities.Film;

public class FilmCheckHelper {

    public static boolean isFillFields(Film film){
        if(film.getName().strip().isEmpty()||film.getCategory().strip().isEmpty()||film.getCoverPhoto().strip().isEmpty()||
                film.getSpeakLanguage().strip().isEmpty()||film.getSummary().strip().isEmpty()||film.getTime().isEmpty()){
          return  false;
        }else{
            return true;
        }
    }
}
