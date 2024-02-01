package com.app.base.project.constant;

public interface BaseFinalConstant {


    interface LanguageConstant {
        String ENGLISH_LANG_PATH = "src/main/resources/string/string_en.json";
        String GERMAN_LANG_PATH = "src/main/resources/string/string_de.json";
    }

    interface API_HEADER {
        String LANGUAGE = "Accept-Language";
        String Authorization = "Authorization";
        String Content_TYPE_JSON = "application/json";
    }

    interface SUPPORTED_LANG {
        String ENGLISH = "en";
        String GERMAN = "de";

    }

    interface IMAGES_FOLDER {
        String PROFILE = "profileImages";
        String VENUE = "venueImages";
        String TEAMS = "teamsImages";
    }

    interface COLUM_DEFAULT {
        String BOOLEAN_DEFAULT_TRUE = "boolean default true";
        String BOOLEAN_DEFAULT_FALSE = "boolean default false";
    }

    interface PAGINATION {
        String PAGE_NUMBER = "0";
        String PAGE_SIZE = "10";
        String SORT_DIRECTION = "asc";
        String SORT_BY = "createdAt";

    }


}