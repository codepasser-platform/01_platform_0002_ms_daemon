package org.codepasser.common.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.toUnsignedLong;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * ID 生成器. [SERVER * 16 + SEQ * 16 + TIME * 32].
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class IdGenerator {

  private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

  private static final IdGenerator instance = new IdGenerator();
  private static final String MACHINE_ID = "MACHINE_ID";
  private static final String YYYY_MM_DD = "yyyy-MM-dd";
  private static final String DEFAULT_TIME = "2018-01-01";
  private static final String DEFAULT_ID = "00";
  private long defaultTime;
  private int machineId;
  private int sequence = 0;
  private long seconds;

  {
    try {
      // settings default machine_id
      defaultTime =
          new SimpleDateFormat(YYYY_MM_DD).parse(DEFAULT_TIME).toInstant().getLong(INSTANT_SECONDS);
      String mid = getenv(MACHINE_ID);
      logger.info("IdGenerator read environment variables that machine sequence is [{}].", mid);
      machineId =
          StringUtils.isEmpty(mid) ? parseInt(getProperty(MACHINE_ID, DEFAULT_ID)) : parseInt(mid);
      logger.info("IdGenerator machine id is [{}].", machineId);
    } catch (ParseException ignore) {
      logger.warn(ignore.getLocalizedMessage());
    }
  }

  public static long next() {
    return instance.generate();
  }

  public static String nextVarchar() {
    return String.valueOf(instance.generate());
  }

  private void calculate() {
    long now = currentSeconds();
    if (now == seconds) {
      if (++sequence > 0xFFFF) {
        logger.warn("sequence overflow, waiting.");
        try {
          wait(100);
          calculate();
        } catch (InterruptedException ignore) {
          logger.warn(ignore.getLocalizedMessage());
        }
      }
    } else {
      seconds = now;
      sequence = 0;
    }
  }

  private long currentSeconds() {
    return new Date().toInstant().getLong(INSTANT_SECONDS) - defaultTime;
  }

  private synchronized long generate() {
    checkArgument(0xFFFFFF >= machineId, "machine id overflow");
    calculate();
    long result = 0;
    result = result | (toUnsignedLong(machineId) << 48);
    result = result | (toUnsignedLong(sequence) << 32);
    result = result | seconds;
    return result;
  }

  //  public static void main(String... args) {
  //    System.out.println(next());
  //    System.out.println(nextVarchar());
  //  }
}
