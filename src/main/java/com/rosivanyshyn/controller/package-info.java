/**
 * This is controller package.
 * <br> It contains dispatcher servlet (Front-controller pattern realization) and next components:
 * <br> context - responsible for managing the objects of an application. It uses dependency injection to achieve inversion of control
 * <br> security - packages responsible for user authorization, authentication and accesses to controllers
 * <br> other - other controllers called through the factory in dispatcher servlet package.
 * <br> tag - tags for JSP pages
 * <br> AppExceptionHandler - application exception handler servlet
 *
 * @author Rostyslav Ivanyshyn.
 */
package com.rosivanyshyn.controller;