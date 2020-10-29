/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package tech.pegasys.ltacfc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesLoader {
  static final Logger LOG = LogManager.getLogger(PropertiesLoader.class);

  public Properties properties = new Properties();


  public PropertiesLoader(String fileName) throws IOException {
    Path propertiesFile = Paths.get(System.getProperty("user.dir"), fileName);
    FileInputStream fis = new FileInputStream(propertiesFile.toFile());
    this.properties.load(fis);
    LOG.info("Loaded properties from file {}", propertiesFile.toString());
  }


  public String getProperty(String prop) {
    String p = this.properties.getProperty(prop);
    if (p == null) {
      LOG.error("Property {} was not defined", prop);
      throw new IllegalArgumentException("Property " + prop + " was not defined");
    }
    return this.properties.getProperty(prop);
  }

  public Credentials getCredentials() {
    return Credentials.create(this.properties.getProperty("PRIVATE_KEY"));
  }
  public Credentials getCredentials(String keyName) {
    return Credentials.create(this.properties.getProperty(keyName));
  }

}
