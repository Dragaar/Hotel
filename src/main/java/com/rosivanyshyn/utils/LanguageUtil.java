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
                    String languageName = rs.getLocale().getLanguage();
                    if(isNotBlank(languageName)) {
                        //System.out.println("Locale -> " + languageName);
                        languageNames.add(languageName);
                    }
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

    private static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
    private static boolean isNotBlank(String value) {
        return !isBlank(value);
    }
}
