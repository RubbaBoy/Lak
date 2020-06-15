package com.uddernetworks.lak.database;

/**
 * An interface meant to abstract the initialization of repositories.
 */
public interface RepositoryInitializer {

    /**
     * Initializes variables and manager data with data from the implementation-specific repository.
     */
    void init();

}
