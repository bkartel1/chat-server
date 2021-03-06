package com.openchat.secureim.server.workers;

import com.openchat.secureim.server.SecureImServerConfiguration;
import com.openchat.secureim.server.storage.Messages;
import net.sourceforge.argparse4j.inf.Namespace;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.openchat.secureim.server.SecureImServerConfiguration;
import com.openchat.secureim.server.storage.Messages;

import java.util.concurrent.TimeUnit;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.ImmutableListContainerFactory;
import io.dropwizard.jdbi.ImmutableSetContainerFactory;
import io.dropwizard.jdbi.OptionalContainerFactory;
import io.dropwizard.jdbi.args.OptionalArgumentFactory;
import io.dropwizard.setup.Bootstrap;

public class TrimMessagesCommand extends ConfiguredCommand<SecureImServerConfiguration> {
  private final Logger logger = LoggerFactory.getLogger(VacuumCommand.class);

  public TrimMessagesCommand() {
    super("trim", "Trim Messages Database");
  }

  @Override
  protected void run(Bootstrap<SecureImServerConfiguration> bootstrap,
                     Namespace namespace,
                     SecureImServerConfiguration config)
      throws Exception
  {
    DataSourceFactory messageDbConfig = config.getMessageStoreConfiguration();
    DBI               messageDbi      = new DBI(messageDbConfig.getUrl(), messageDbConfig.getUser(), messageDbConfig.getPassword());

    messageDbi.registerArgumentFactory(new OptionalArgumentFactory(messageDbConfig.getDriverClass()));
    messageDbi.registerContainerFactory(new ImmutableListContainerFactory());
    messageDbi.registerContainerFactory(new ImmutableSetContainerFactory());
    messageDbi.registerContainerFactory(new OptionalContainerFactory());

    Messages messages  = messageDbi.onDemand(Messages.class);
    long     timestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(60);

    logger.info("Trimming old messages: " + timestamp + "...");
    messages.removeOld(timestamp);

    Thread.sleep(3000);
    System.exit(0);
  }
}
