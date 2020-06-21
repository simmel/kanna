package se.soy.kanna;

public class Main {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

  public static void main(String[] args) throws Exception {
    log.info("Starting Kanna");

    org.apache.camel.blueprint.Main main = new org.apache.camel.blueprint.Main();
    // We must include our own bundle or otherwise supplied blueprint won't be
    // started.
    main.setIncludeSelfAsBundle(true);

    // Run until terminated
    main.run(args);
  }
}
