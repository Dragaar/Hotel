package com.rosivanyshyn.utils;

import com.rosivanyshyn.exeption.AppException;

import java.util.*;

public class LanguageUtil {
    private static ArrayList<String> languageNames;
    private static Set<ResourceBundle> resourceBundles;

    public static ArrayList<String> getLanguageNamesList(){

        LanguageUtil.getResourceBundles("language");

        if(!resourceBundles.isEmpty()) {
            if (languageNames == null) {
                languageNames = new ArrayList<>();
                for (ResourceBundle rs : resourceBundles) {
                    //System.out.println("Locale -> " + rs.getLocale().getLanguage());
                    languageNames.add(rs.getLocale().getLanguage());
                }
            }
            return languageNames;
        } else return null;
    }

    public static Set<ResourceBundle> getResourceBundles(String baseName) {

        if (resourceBundles == null) {
            resourceBundles = new HashSet<>();
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    resourceBundles.add(ResourceBundle.getBundle(baseName, locale));
                } catch (MissingResourceException ex) {
                // do nothing
                }
            }
        }
        return Collections.unmodifiableSet(resourceBundles);


    }
}
