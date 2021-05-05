package cz.inventi.qa.framework.tests.core;

import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.objects.test.TestBase;

/**
 * Dummy parent class providing custom config files.
 */
@ConfigFiles(driverConfig = "customWebDriverConfig.yml", appsConfig = "customAppsConfig.yml")
public abstract class CustomConfigTest extends TestBase {
}
