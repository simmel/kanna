package se.soy.kanna;

public class Main {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

  public static void main(String[] args) {
    log.info("Hello world!");
  }
}
