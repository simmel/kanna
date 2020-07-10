package se.soy.kanna;
import java.util.LinkedList;

public class Main extends org.apache.camel.blueprint.Main {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

  public Main() {
    super();

    addOption(new ParameterOption("is", "includeSelfAsBundle",
          "Include self as Bundle", "includeSelfAsBundle") {
      protected void doProcess(String arg, String parameter, LinkedList<String> remainingArgs) {
        setIncludeSelfAsBundle(Boolean.parseBoolean(parameter));
      }
    });

    addOption(new ParameterOption("bn", "bundleName",
          "Set the name of the bundle", "bundleName") {
      protected void doProcess(String arg, String parameter, LinkedList<String> remainingArgs) {
        setBundleName(parameter);
      }
    });
  }

  public static void main(String...args) throws Exception {
    log.info("Starting Kanna");

    Main main = new Main();
    // We must include our own bundle or otherwise supplied blueprint won't be
    // started.
    main.setIncludeSelfAsBundle(true);

    // Run until terminated
    main.run(args);
  }
}
