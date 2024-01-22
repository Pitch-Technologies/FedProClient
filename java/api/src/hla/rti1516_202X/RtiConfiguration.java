/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: RtiConfiguration.java

package hla.rti1516_202X;

import java.io.Serializable;

public class RtiConfiguration implements Serializable {
   private String _configurationName = "";
   private String _rtiAddress = "";
   private String _additionalSettings = "";
   public static RtiConfiguration createConfiguration()
   {
      return new RtiConfiguration();
   }

   public RtiConfiguration withConfigurationName(String configurationName)
   {
      _configurationName = configurationName;
      return this;
   }

   public RtiConfiguration withRtiAddress(String rtiAddress)
   {
      _rtiAddress = rtiAddress;
      return this;
   }

   public RtiConfiguration withAdditionalSettings(String additionalSettings)
   {
      _additionalSettings = additionalSettings;
      return this;
   }

   public String configurationName()
   {
      return _configurationName;
   }

   public String rtiAddress()
   {
      return _rtiAddress;
   }

   public String additionalSettings()
   {
      return _additionalSettings;
   }

   protected RtiConfiguration()
   {
   }
}
