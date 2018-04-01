package worker;

import main.Configuration;

/**
 * Class from which the Workers should inherit
 */
public abstract class Worker implements Runnable {
    Configuration configuration;

    public Worker(Configuration configuration) {
        this.configuration = configuration;
    }
}
